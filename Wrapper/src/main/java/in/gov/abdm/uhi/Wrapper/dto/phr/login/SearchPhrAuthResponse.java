package in.gov.abdm.uhi.Wrapper.dto.phr.login;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import in.gov.abdm.uhi.Wrapper.dto.phr.ServiceResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchPhrAuthResponse extends ServiceResponse {
    private Set<String> authMethods;
    @NotNull(message = "phrAddress cannot be null")
    private Object phrAddress;
}
