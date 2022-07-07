<<<<<<< HEAD
<<<<<<< HEAD:UserManagement/src/main/java/in/gov/abdm/eua/userManagement/dto/dhp/Error.java
package in.gov.abdm.eua.userManagement.dto.dhp;
=======
package in.gov.abdm.eua.service.dto.dhp;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a:EUAclient/src/main/java/in/gov/abdm/eua/service/dto/dhp/Error.java
=======
package in.gov.abdm.eua.userManagement.dto.dhp;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Error {
    public String type;
    public String code;
    public String path;
    public String message;
}
