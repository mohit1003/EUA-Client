<<<<<<< HEAD
<<<<<<< HEAD:UserManagement/src/main/java/in/gov/abdm/eua/userManagement/dto/phr/registration/LoginRequestPayload.java
package in.gov.abdm.eua.userManagement.dto.phr.registration;
=======
package in.gov.abdm.eua.service.dto.phr.registration;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a:EUAclient/src/main/java/in/gov/abdm/eua/service/dto/phr/registration/LoginRequestPayload.java
=======
package in.gov.abdm.eua.userManagement.dto.phr.registration;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRequestPayload {
    @NotNull(message = "sessionId cannot be null")
    private Object sessionId;
    @NotNull(message = "value cannot be null")
    private Object value;
}
