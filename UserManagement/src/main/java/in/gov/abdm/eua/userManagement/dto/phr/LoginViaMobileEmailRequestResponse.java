<<<<<<< HEAD
<<<<<<< HEAD:UserManagement/src/main/java/in/gov/abdm/eua/userManagement/dto/phr/LoginViaMobileEmailRequestResponse.java
package in.gov.abdm.eua.userManagement.dto.phr;
=======
package in.gov.abdm.eua.service.dto.phr;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a:EUAclient/src/main/java/in/gov/abdm/eua/service/dto/phr/LoginViaMobileEmailRequestResponse.java
=======
package in.gov.abdm.eua.userManagement.dto.phr;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginViaMobileEmailRequestResponse extends ServiceResponse {
    @NotNull(message = "TransactionId cannot be null")
    private Object transactionId;
    @NotNull(message = "TransactionId cannot be null")
    private String requesterId;
    @NotBlank(message = "TransactionId cannot be null")
    private String authMode;


}
