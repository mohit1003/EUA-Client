package in.gov.abdm.eua.client.service;

import com.rabbitmq.client.Channel;
import in.gov.abdm.eua.client.constants.ConstantsUtils;
import in.gov.abdm.eua.client.dto.dhp.EuaRequestBody;
import in.gov.abdm.eua.client.dto.dhp.MessageTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;

public interface MQConsumerService {
    void getAckResponseResponseEntity(EuaRequestBody request, Channel channel, long tag);

    void sendNackResponse(String nack, String messageId);

    @RabbitListener(queues = ConstantsUtils.QUEUE_EUA_TO_GATEWAY)
    void euaToGatewayConsumer(MessageTO message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException;

    @RabbitListener(queues = ConstantsUtils.QUEUE_GATEWAY_TO_EUA)
    void gatewayToEuaConsumer(MessageTO response);
}
