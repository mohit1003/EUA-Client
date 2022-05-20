package in.gov.abdm.eua.client.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import in.gov.abdm.eua.client.constants.ConstantsUtils;
import in.gov.abdm.eua.client.dto.dhp.AckResponse;
import in.gov.abdm.eua.client.dto.dhp.EuaRequestBody;
import in.gov.abdm.eua.client.dto.dhp.MessageTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class MQConsumerServiceImpl implements in.gov.abdm.eua.client.service.MQConsumerService {
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
                .retrieve() // By default .retrieve() will check for error statuses for you. TODO 1
                .bodyToMono(AckResponse.class);

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
}
