package in.gov.abdm.eua.client.dto.phr.login;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import in.gov.abdm.eua.client.dto.phr.Requester;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginViaMobileEmailRequestInit {
    @NotBlank(message = "value cannot be null/blank")
    private String value;
    @NotBlank(message = "purpose cannot be null/blank")
    private String purpose;
    @NotBlank(message = "authMode cannot be null/blank")
    private String authMode;
    @NotNull(message = "requester cannot be null/blank")
    @Valid
    private Requester requester;
}
