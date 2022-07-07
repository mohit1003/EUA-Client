<<<<<<< HEAD
<<<<<<< HEAD:UserManagement/src/main/java/in/gov/abdm/eua/userManagement/dto/phr/ResendLoginOTPRequest.java
package in.gov.abdm.eua.userManagement.dto.phr;
=======
package in.gov.abdm.uhi.Wrapper.dto.phr;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a:Wrapper/src/main/java/in/gov/abdm/uhi/Wrapper/dto/phr/ResendLoginOTPRequest.java
=======
package in.gov.abdm.eua.userManagement.dto.phr;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResendLoginOTPRequest {
    private String txnId;

    private String authMethod;


}
