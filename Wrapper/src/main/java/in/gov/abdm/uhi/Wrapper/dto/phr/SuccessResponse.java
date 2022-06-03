package in.gov.abdm.uhi.Wrapper.dto.phr;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class SuccessResponse extends ServiceResponse {

    protected Boolean success;
    protected Object sessionId;
}
