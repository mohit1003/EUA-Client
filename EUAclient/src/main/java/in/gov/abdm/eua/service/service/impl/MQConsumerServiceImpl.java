package in.gov.abdm.eua.service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import in.gov.abdm.eua.service.constants.ConstantsUtils;
import in.gov.abdm.eua.service.dto.dhp.AckResponse;
import in.gov.abdm.eua.service.dto.dhp.EuaRequestBody;
import in.gov.abdm.eua.service.dto.dhp.MessageTO;
import in.gov.abdm.eua.service.exceptions.PhrException400;
import in.gov.abdm.eua.service.exceptions.PhrException500;
import in.gov.abdm.eua.service.service.MQConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class MQConsumerServiceImpl implements MQConsumerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MQConsumerServiceImpl.class);

    @Value("${abdm.gateway.url}")
    private String abdmGatewayUrl;

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

//        abdmGatewayUrl = ConstantsUtils.GATEWAY_URL;
        LOGGER.info("abdmGatewayUrl is "+abdmGatewayUrl);
    }

    @Override
    public Mono<AckResponse> getAckResponseResponseEntity(EuaRequestBody request, String bookignServiceUrl) {
        String url = null;

        url = getAppropriateUrl(request, bookignServiceUrl);
        url = url.concat("/"+request.getContext().getAction());
        LOGGER.info("URL in MQCOnsumerService is "+url);
        LOGGER.info("Context.Action is  "+request.getContext().getAction());
        LOGGER.info("Message ID is "+ request.getContext().getMessageId());

        Mono<AckResponse> ackResponseMono = this.webClient.post().uri(url)
                .header("authorization","UHI")
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(AckResponse.class)
                .onErrorResume(this::getErrorSchemaReady);

        String finalUrl = url;

        ackResponseMono.subscribe(res -> {
            LOGGER.info("Inside subscribe :: URL is :: "+ finalUrl);
            LOGGER.info("Response from webclient call is ====> "+res);
            messagingTemplate.convertAndSendToUser(request.getContext().getMessageId(), ConstantsUtils.QUEUE_SPECIFIC_USER, res);
        });

        return ackResponseMono;
    }

    private String getAppropriateUrl(EuaRequestBody request, String bookignServiceUrl) {
        String url;
        if( null != request.getContext().getProviderUri()) {
            LOGGER.info("providerUrl :: "+ request.getContext().getProviderUri());
            url = request.getContext().getProviderUri();
        }else {
            LOGGER.info("GatewayUrl :: "+ abdmGatewayUrl);
            url = ConstantsUtils.GATEWAY_URL;
        }
        if(null != bookignServiceUrl) {
            LOGGER.info("BookingServiceUrl :: "+ bookignServiceUrl);
                url = bookignServiceUrl;
        }
        return url;
    }

    @Override
    public void sendNackResponse(String nack, String messageId) {
        LOGGER.info("Exception occurred. Message ID is "+ messageId+" Sending NACK response");
        messagingTemplate.convertAndSendToUser(messageId, ConstantsUtils.QUEUE_SPECIFIC_USER, nack);
    }


    @Override
    @RabbitListener(queues = ConstantsUtils.QUEUE_EUA_TO_GATEWAY)
    public void euaToGatewayConsumer(MessageTO message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        LOGGER.info("Message read from MQ EUA_TO_GATEWAY::" + message);
        EuaRequestBody request = objectMapper.readValue(message.getResponse(), EuaRequestBody.class);
        if("on_init".equals(request.getContext().getAction()) && "on_confirm".equals(request.getContext().getAction())) {
            getAckResponseResponseEntity(request, ConstantsUtils.BOOKING_SERVICE_URL);
        }
        else{
            getAckResponseResponseEntity(request, null);
        }

    }

    @Override
    @RabbitListener(queues = ConstantsUtils.QUEUE_GATEWAY_TO_EUA)
    public void gatewayToEuaConsumer(MessageTO response) {
        LOGGER.info("Message read from MQ GATEWAY_TO_EUA::" + response);
        messagingTemplate.convertAndSendToUser(response.getMessageId(),"/queue/specific-user", response);
    }

    private Mono<AckResponse> getErrorSchemaReady(Throwable error)  {
        LOGGER.error("MQConsumerService::error::onErrorResume::" + error.getMessage());

        AckResponse ackResponseErr = null;
        try {
            ackResponseErr = prepareErrorObjectAck();
        } catch (JsonProcessingException e) {
           LOGGER.error(e.getMessage());
        }
        Mono<AckResponse> errorMono = null;
        if (ackResponseErr != null) {
            errorMono = Mono.just(ackResponseErr);

            errorMono.subscribe(err -> {
                LOGGER.error(error.getLocalizedMessage());
                err.getError().setCode(error.getLocalizedMessage());
                err.getError().setCode("500");
                err.getError().setPath("MQConsumerService");
            });
        }
            return errorMono;
    }

    private AckResponse prepareErrorObjectAck() throws JsonProcessingException {

        return objectMapper.readValue(ConstantsUtils.NACK_RESPONSE,AckResponse.class);
    }
}
