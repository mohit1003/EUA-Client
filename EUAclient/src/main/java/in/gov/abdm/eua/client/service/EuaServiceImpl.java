package in.gov.abdm.eua.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.gov.abdm.eua.client.dto.dhp.AckResponse;
import in.gov.abdm.eua.client.dto.dhp.EuaRequestBody;
import in.gov.abdm.eua.client.dto.dhp.MessageTO;
import in.gov.abdm.eua.client.model.Message;
import org.springframework.http.ResponseEntity;

public interface EuaServiceImpl {
    Message pushToMqAndSaveToDb(MessageTO message) throws JsonProcessingException;

    void pushToMq(String abdmGatewayURl, String searchEndpoint, EuaRequestBody searchRequest, String requestString, String requestMessageId, String clientId);

    MessageTO extractMessage(String requestMessageId, String consumerId, String onRequestStringResponse, String dhpQueryType);

    ResponseEntity<AckResponse> getOnAckResponseResponseEntity(ObjectMapper objectMapper, String onRequest, String dhpQueryType) throws JsonProcessingException;
}
