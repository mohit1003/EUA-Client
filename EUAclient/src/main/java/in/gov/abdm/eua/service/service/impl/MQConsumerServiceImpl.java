package in.gov.abdm.eua.service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import in.gov.abdm.eua.service.constants.ConstantsUtils;
import in.gov.abdm.eua.service.dto.dhp.AckResponse;
import in.gov.abdm.eua.service.dto.dhp.EuaRequestBody;
import in.gov.abdm.eua.service.dto.dhp.MessageTO;
import in.gov.abdm.eua.service.dto.phr.ServiceResponse;
import in.gov.abdm.eua.service.exceptions.PhrException400;
import in.gov.abdm.eua.service.exceptions.PhrException500;
import in.gov.abdm.eua.service.service.MQConsumerService;
import in.gov.abdm.uhi.common.dto.AckTO;
import in.gov.abdm.uhi.common.dto.ErrorTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class MQConsumerServiceImpl implements MQConsumerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MQConsumerServiceImpl.class);

    final
    ObjectMapper objectMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final WebClient webClient;
    final
    SimpUserRegistry simpUserRegistry;



    public MQConsumerServiceImpl(ObjectMapper objectMapper, SimpMessagingTemplate template, WebClient webClient, SimpUserRegistry simpUserRegistry) {
        this.objectMapper = objectMapper;
        this.messagingTemplate = template;
        this.webClient = webClient;
        this.simpUserRegistry = simpUserRegistry;
    }

    @Override
    public void getAckResponseResponseEntity(EuaRequestBody request, Channel channel, long tag) {

        Mono<AckResponse> ackResponseMono = webClient.post().uri(request.getContext().getProviderUri()+"/"+request.getContext().getAction()).body(Mono.just(request), AckResponse.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(AckResponse.class)
                .onErrorResume(this::getErrorSchemaReady);

        ackResponseMono.subscribe(ackResponse -> {
            if(ackResponse.getMessage().getAck().getStatus().equalsIgnoreCase("ack")) {
                try {
                    channel.basicAck(tag,false);
                } catch (IOException e) {
                   LOGGER.error(e.getMessage());
                }
            }
            messagingTemplate.convertAndSendToUser(request.getContext().getMessageId(),"/queue/specific-user", ackResponse);
        });

    }

    @Override
    public void sendNackResponse(String nack, String messageId) {
        messagingTemplate.convertAndSendToUser(messageId,"/queue/specific-user", nack);

    }


    @Override
    @RabbitListener(queues = ConstantsUtils.QUEUE_EUA_TO_GATEWAY)
    public void euaToGatewayConsumer(MessageTO message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
       EuaRequestBody request = objectMapper.readValue(message.getResponse(), EuaRequestBody.class);
        getAckResponseResponseEntity(request, channel, tag);
    }

    @Override
    @RabbitListener(queues = ConstantsUtils.QUEUE_GATEWAY_TO_EUA)
    public void gatewayToEuaConsumer(MessageTO response) {
//        simpUserRegistry.getUsers().stream().forEach(m->System.out.println("cdsdcsdc "+m));
        messagingTemplate.convertAndSendToUser(response.getMessageId(),"/queue/specific-user", response);
    }

    private Mono<AckResponse> getErrorSchemaReady(Throwable error)  {
        LOGGER.error("PhrLoginMobileEmailController::error::onErrorResume::" + error.getMessage());

        AckResponse ackResponseErr = null;
        try {
            ackResponseErr = prepareErrorObjectAck();
        } catch (JsonProcessingException e) {
           LOGGER.error(e.getMessage());
        }
        Mono<AckResponse> errorMono = Mono.just(ackResponseErr);
        errorMono.subscribe(err -> {
            LOGGER.error( error.getLocalizedMessage());
            err.getError().setCode( error.getLocalizedMessage());
            err.getError().setCode("500");
            err.getError().setPath("/registration/mobileEmail/validate/otp");
        });
        return errorMono;
    }

    private AckResponse prepareErrorObjectAck() throws JsonProcessingException {

        AckResponse ackResponseError = objectMapper.readValue(ConstantsUtils.NACK_RESPONSE,AckResponse.class);

        return ackResponseError;
    }
}
