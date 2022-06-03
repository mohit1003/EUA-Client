package in.gov.abdm.eua.service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.gov.abdm.eua.service.constants.ConstantsUtils;
import in.gov.abdm.eua.service.dto.dhp.AckResponse;
import in.gov.abdm.eua.service.dto.dhp.EuaRequestBody;
import in.gov.abdm.eua.service.dto.dhp.MessageTO;
import in.gov.abdm.eua.service.model.Message;
import in.gov.abdm.eua.service.repository.EuaRepository;
import in.gov.abdm.eua.service.service.EuaService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class EuaServiceImpl implements EuaService {

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
    public void pushToMqGatewayTOEua(MessageTO message) throws JsonProcessingException {
        template.convertAndSend(ConstantsUtils.EXCHANGE, ConstantsUtils.ROUTING_KEY_GATEWAY_TO_EUA, message);

//        Message messageModel = modelMapper.map(message, Message.class);
//        return euaRepository.save(messageModel);

    }

    @Override
    public void pushToMq(String abdmGatewayURl, String searchEndpoint, EuaRequestBody searchRequest, String requestString, String requestMessageId, String clientId) {
        String url;
        url = abdmGatewayURl + searchEndpoint;
        searchRequest.getContext().setProviderUri(url);

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
    public ResponseEntity<AckResponse> getOnAckResponseResponseEntity(ObjectMapper objectMapper, String onRequest, String dhpQueryType) throws com.fasterxml.jackson.core.JsonProcessingException {
        if (onRequest == null) {

            String onSearchAckJsonErrorString = "{ \"message\": { \"ack\": { \"status\": \"NACK\" } }, \"error\": { \"type\": \"\", \"code\": \"400\", \"path\": \"string\", \"message\": \"Message ID not present\" } }";
            AckResponse onSearchAck = objectMapper.readValue(NACK_RESPONSE, AckResponse.class);
            return new ResponseEntity<>(onSearchAck, HttpStatus.OK);

        } else {
//			EuaRequestBody euaRequestBody;
//			EuaRequestBodyStatus euaRequestBodyStatus;
//			if(dhpQueryType.equals("on_init") || dhpQueryType.equals("on_confirm") || dhpQueryType.equals("on_status")) {
//				euaRequestBodyStatus = objectMapper.readValue(onRequest, EuaRequestBodyStatus.class);
//				messageRepository.save(new Message(euaRequestBodyStatus.getContext().getMessage_id(), onRequest, dhpQueryType,Timestamp.from(ZonedDateTime.now().toInstant()),euaRequestBodyStatus.getContext().getConsumer_id()));
//
//			}
//			if(dhpQueryType.equals("on_search") || dhpQueryType.equals("on_select")) {
//				euaRequestBody = objectMapper.readValue(onRequest, EuaRequestBody.class);
//				messageRepository.save(new Message(euaRequestBody.getContext().getMessage_id(), onRequest, dhpQueryType, Timestamp.from(ZonedDateTime.now().toInstant()),euaRequestBody.getContext().getConsumer_id()));
//
//			}

//			Optional<Message> message = messageData.get(messageData.size()-1);
//			if(Optional.ofNullable(message).isPresent()) {
//				Message messageFromDb = message.get();
//				messageFromDb.setResponse(onRequestString);

//			}
            String onRequestAckJsonString = "{ \"message\": { \"ack\": { \"status\": \"ACK\" } } }";
            AckResponse onSearchAck = objectMapper.readValue(onRequestAckJsonString, AckResponse.class);
            return new ResponseEntity<>(onSearchAck, HttpStatus.OK);

        }
    }
}
