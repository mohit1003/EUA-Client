<<<<<<< HEAD
<<<<<<< HEAD:UserManagement/src/main/java/in/gov/abdm/eua/userManagement/dto/phr/login/VerifyPasswordOtpLoginRequest.java
package in.gov.abdm.eua.userManagement.dto.phr.login;
=======
package in.gov.abdm.uhi.Wrapper.dto.phr.login;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a:Wrapper/src/main/java/in/gov/abdm/uhi/Wrapper/dto/phr/login/VerifyPasswordOtpLoginRequest.java
=======
package in.gov.abdm.eua.userManagement.dto.phr.login;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

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

    @NotBlank(message = "patientId cannot be blank")
    private String patientId;
}
