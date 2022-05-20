package in.gov.abdm.eua.client.dto.dhp;


import com.fasterxml.jackson.annotation.JsonProperty;
import in.gov.abdm.uhi.common.dto.ContextTO;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EuaRequestBodyStatus {
    private ContextTO context;
    private OnSearchMessageTO message;
    @JsonProperty("client_id")
    private String clientId;
}
