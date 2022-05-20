package in.gov.abdm.eua.client.dto.phr.registration;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.gov.abdm.eua.client.dto.phr.ServiceResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtResponseHid extends ServiceResponse {
    @NotNull(message = "phrAddress cannot be null")
    private Object phrAdress;
    private Object token;

}
