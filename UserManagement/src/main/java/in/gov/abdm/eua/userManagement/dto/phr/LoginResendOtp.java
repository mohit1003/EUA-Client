<<<<<<< HEAD
<<<<<<< HEAD:UserManagement/src/main/java/in/gov/abdm/eua/userManagement/dto/phr/LoginResendOtp.java
package in.gov.abdm.eua.userManagement.dto.phr;
=======
package in.gov.abdm.eua.service.dto.phr;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a:EUAclient/src/main/java/in/gov/abdm/eua/service/dto/phr/LoginResendOtp.java
=======
package in.gov.abdm.eua.userManagement.dto.phr;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResendOtp extends ServiceResponse {
    @NotBlank(message = "transactionId cannot be null/blank")
    private String transactionId;
}
