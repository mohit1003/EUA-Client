package in.gov.abdm.eua.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.client.Channel;
import in.gov.abdm.eua.service.constants.ConstantsUtils;
import in.gov.abdm.eua.service.dto.dhp.AckResponseDTO;
import in.gov.abdm.eua.service.dto.dhp.EuaRequestBody;
import in.gov.abdm.eua.service.dto.dhp.MqMessageTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface MQConsumerService {

    Mono<AckResponseDTO> getAckResponseResponseEntity(EuaRequestBody request, String bookignServiceUrl);

    void prepareAndSendNackResponse(String nack, String messageId);

    @RabbitListener(queues = ConstantsUtils.QUEUE_EUA_TO_GATEWAY)
    void euaToGatewayConsumer(MqMessageTO request, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException;

    @RabbitListener(queues = ConstantsUtils.QUEUE_GATEWAY_TO_EUA)
    void gatewayToEuaConsumer(MqMessageTO response) throws JsonProcessingException;
}
