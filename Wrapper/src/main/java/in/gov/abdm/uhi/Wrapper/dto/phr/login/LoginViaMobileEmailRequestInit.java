package in.gov.abdm.uhi.Wrapper.dto.phr.login;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import in.gov.abdm.uhi.Wrapper.dto.phr.Requester;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
