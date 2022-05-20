package in.gov.abdm.eua.client.dto.phr.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VerifyPasswordOtpLoginRequest {
    @NotBlank(message = "transactionId cannot be null")
    private String transactionId;
    @NotBlank(message = "authCode cannot be null")
    private String authCode;
    @NotBlank(message = "requesterId cannot be null")
    private String requesterId;
}
