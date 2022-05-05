package in.gov.abdm.eua.client.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import in.gov.abdm.eua.client.beans.Context;
import in.gov.abdm.eua.client.beans.OnMessage;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EuaRequestBodyStatus {
    private Context context;
    private OnMessage message;
    @JsonProperty("client_id")
    private String clientId;
}
