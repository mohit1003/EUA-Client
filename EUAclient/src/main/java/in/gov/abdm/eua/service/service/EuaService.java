package in.gov.abdm.eua.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.gov.abdm.eua.service.dto.dhp.AckResponse;
import in.gov.abdm.eua.service.dto.dhp.EuaRequestBody;
import in.gov.abdm.eua.service.dto.dhp.MessageTO;
import org.springframework.http.ResponseEntity;

public interface EuaService {
    void pushToMqGatewayTOEua(MessageTO message, String requestMessageId) throws JsonProcessingException;

    void pushToMq(EuaRequestBody searchRequest, String requestString, String requestMessageId, String clientId);

    MessageTO extractMessage(String requestMessageId, String consumerId, String onRequestStringResponse, String dhpQueryType);

    ResponseEntity<AckResponse> getOnAckResponseResponseEntity(ObjectMapper objectMapper, String onRequest, String dhpQueryType, String requestMessageId) throws JsonProcessingException;
}
