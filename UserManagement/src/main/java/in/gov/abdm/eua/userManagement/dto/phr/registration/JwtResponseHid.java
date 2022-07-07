<<<<<<< HEAD
<<<<<<< HEAD:UserManagement/src/main/java/in/gov/abdm/eua/userManagement/dto/phr/registration/JwtResponseHid.java
=======
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a
package in.gov.abdm.eua.userManagement.dto.phr.registration;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.gov.abdm.eua.userManagement.dto.phr.ServiceResponse;
<<<<<<< HEAD
=======
package in.gov.abdm.uhi.Wrapper.dto.phr.registration;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import in.gov.abdm.uhi.Wrapper.dto.phr.ServiceResponse;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a:Wrapper/src/main/java/in/gov/abdm/uhi/Wrapper/dto/phr/registration/JwtResponseHid.java
=======
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

<<<<<<< HEAD
=======
import javax.validation.constraints.NotNull;

>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtResponseHid extends ServiceResponse {
    @NotNull(message = "phrAddress cannot be null")
    private Object phrAdress;
    private Object token;
    private String txnId;
}
