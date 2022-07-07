<<<<<<< HEAD
<<<<<<< HEAD:UserManagement/src/main/java/in/gov/abdm/eua/userManagement/dto/phr/login/SearchByHealthIdNumberRequest.java
package in.gov.abdm.eua.userManagement.dto.phr.login;
=======
package in.gov.abdm.uhi.Wrapper.dto.phr.login;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a:Wrapper/src/main/java/in/gov/abdm/uhi/Wrapper/dto/phr/login/SearchByHealthIdNumberRequest.java
=======
package in.gov.abdm.eua.userManagement.dto.phr.login;
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
public class SearchByHealthIdNumberRequest {

    @NotNull(message = "healthIdNumber cannot be null")
    private Object healthIdNumber;
    @NotNull(message = "yearOfBirth")
    private Object yearOfBirth;

}
