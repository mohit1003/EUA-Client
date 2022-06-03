package in.gov.abdm.eua.service.dto.dhp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import in.gov.abdm.uhi.common.dto.ContextTO;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EuaRequestBodyStatus {
    private ContextTO context;
    private OnSearchMessageTO message;
    @JsonProperty("client_id")
    private String clientId;
}
