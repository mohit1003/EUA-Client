<<<<<<< HEAD
<<<<<<< HEAD:UserManagement/src/main/java/in/gov/abdm/eua/userManagement/dto/phr/SuccessResponse.java
package in.gov.abdm.eua.userManagement.dto.phr;
=======
package in.gov.abdm.eua.service.dto.phr;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a:EUAclient/src/main/java/in/gov/abdm/eua/service/dto/phr/SuccessResponse.java
=======
package in.gov.abdm.eua.userManagement.dto.phr;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class SuccessResponse extends ServiceResponse {

    protected Boolean success;
    protected Object sessionId;
}
