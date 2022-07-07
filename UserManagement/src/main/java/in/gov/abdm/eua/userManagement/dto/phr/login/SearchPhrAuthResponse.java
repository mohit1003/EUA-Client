<<<<<<< HEAD
<<<<<<< HEAD:UserManagement/src/main/java/in/gov/abdm/eua/userManagement/dto/phr/login/SearchPhrAuthResponse.java
=======
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a
package in.gov.abdm.eua.userManagement.dto.phr.login;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.gov.abdm.eua.userManagement.dto.phr.ServiceResponse;
<<<<<<< HEAD
=======
package in.gov.abdm.eua.service.dto.phr.login;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.gov.abdm.eua.service.dto.phr.ServiceResponse;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a:EUAclient/src/main/java/in/gov/abdm/eua/service/dto/phr/login/SearchPhrAuthResponse.java
=======
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Set;

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
