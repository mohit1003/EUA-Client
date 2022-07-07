<<<<<<< HEAD
<<<<<<< HEAD:UserManagement/src/main/java/in/gov/abdm/eua/userManagement/dto/phr/error/ErrorFromServer.java
package in.gov.abdm.eua.userManagement.dto.phr.error;
=======
package in.gov.abdm.eua.service.dto.phr.error;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a:EUAclient/src/main/java/in/gov/abdm/eua/service/dto/phr/error/ErrorFromServer.java
=======
package in.gov.abdm.eua.userManagement.dto.phr.error;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a

import lombok.Data;

@Data
public class ErrorFromServer {
    private ErrorClass error;

    @Data
    public class ErrorClass {
        private String code;
        private String message;
    }
}
