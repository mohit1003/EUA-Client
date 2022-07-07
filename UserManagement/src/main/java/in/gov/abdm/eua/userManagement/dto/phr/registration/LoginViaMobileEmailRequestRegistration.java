<<<<<<< HEAD
<<<<<<< HEAD:UserManagement/src/main/java/in/gov/abdm/eua/userManagement/dto/phr/registration/LoginViaMobileEmailRequestRegistration.java
package in.gov.abdm.eua.userManagement.dto.phr.registration;
=======
package in.gov.abdm.uhi.Wrapper.dto.phr.registration;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a:Wrapper/src/main/java/in/gov/abdm/uhi/Wrapper/dto/phr/registration/LoginViaMobileEmailRequestRegistration.java
=======
package in.gov.abdm.eua.userManagement.dto.phr.registration;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginViaMobileEmailRequestRegistration {
    @JsonProperty("healthid")
    @NotBlank(message = "HealthId cannot be null/blank")
    private String healthId;
    @NotBlank(message = "authMethod cannot be null/blank")
    private String authMethod;
}
