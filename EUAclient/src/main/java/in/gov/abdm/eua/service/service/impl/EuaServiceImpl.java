package in.gov.abdm.eua.service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.gov.abdm.eua.service.constants.ConstantsUtils;
import in.gov.abdm.eua.service.dto.dhp.AckResponse;
import in.gov.abdm.eua.service.dto.dhp.EuaRequestBody;
import in.gov.abdm.eua.service.dto.dhp.MessageTO;
import in.gov.abdm.eua.service.repository.EuaRepository;
import in.gov.abdm.eua.service.service.EuaService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
public class EuaServiceImpl implements EuaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EuaServiceImpl.class);
    private final RabbitTemplate template;
    private final EuaRepository euaRepository;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    private final String NACK_RESPONSE = "{ \"message\": { \"ack\": { \"status\": \"NACK\" } }, \"error\": { \"type\": \"\", \"code\": \"500\", \"path\": \"string\", \"message\": \"Something went wrong\" } }";

    public EuaServiceImpl(RabbitTemplate template, EuaRepository euaRepository, ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.template = template;
        this.euaRepository = euaRepository;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public void pushToMqGatewayTOEua(MessageTO message, String requestMessageId) {
        LOGGER.info("Pushing to MQ. Message ID is "+ requestMessageId);
        LOGGER.info("Inside GATEWAY_TO_EUA queue convertAndSend... Queue name is =====> "+ConstantsUtils.ROUTING_KEY_GATEWAY_TO_EUA);
        template.convertAndSend(ConstantsUtils.EXCHANGE, ConstantsUtils.ROUTING_KEY_GATEWAY_TO_EUA, message);


    }

    @Override
    public void pushToMq(EuaRequestBody searchRequest, String requestString, String requestMessageId, String clientId) {
        LOGGER.info("Pushing to MQ. Message ID is "+ requestMessageId);
        MessageTO message = extractMessage(requestMessageId, clientId, requestString,searchRequest.getContext().getAction());
        template.convertAndSend(ConstantsUtils.EXCHANGE, ConstantsUtils.ROUTING_KEY_EUA_TO_GATEWAY, message);
    }

    @Override
    public MessageTO extractMessage(String requestMessageId, String consumerId, String onRequestStringResponse, String dhpQueryType) {
        MessageTO message = new MessageTO();
        message.setMessageId(requestMessageId);
        message.setConsumerId(consumerId);
        message.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString());
        message.setDhpQueryType(dhpQueryType);
        message.setResponse(onRequestStringResponse);

        return message;
    }


    @Override
    public ResponseEntity<AckResponse> getOnAckResponseResponseEntity(ObjectMapper objectMapper, String onRequest, String dhpQueryType, String requestMessageId) throws com.fasterxml.jackson.core.JsonProcessingException {
        if (onRequest == null) {
            LOGGER.error("ERROR. Request is null"+ requestMessageId);
            LOGGER.error("Error with response. Response is "+onRequest);
            AckResponse onSearchAck = objectMapper.readValue(NACK_RESPONSE, AckResponse.class);
            return new ResponseEntity<>(onSearchAck, HttpStatus.INTERNAL_SERVER_ERROR);

        } else {
            LOGGER.info("Success... Message ID is"+ requestMessageId);
            String onRequestAckJsonString = "{ \"message\": { \"ack\": { \"status\": \"ACK\" } } }";
            AckResponse onSearchAck = objectMapper.readValue(onRequestAckJsonString, AckResponse.class);
            return new ResponseEntity<>(onSearchAck, HttpStatus.OK);

        }
    }
}
