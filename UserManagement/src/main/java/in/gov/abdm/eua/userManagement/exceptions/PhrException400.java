<<<<<<< HEAD
<<<<<<< HEAD:UserManagement/src/main/java/in/gov/abdm/eua/userManagement/exceptions/PhrException400.java
package in.gov.abdm.eua.userManagement.exceptions;
=======
package in.gov.abdm.eua.service.exceptions;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a:EUAclient/src/main/java/in/gov/abdm/eua/service/exceptions/PhrException400.java
=======
package in.gov.abdm.eua.userManagement.exceptions;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a

import org.hibernate.service.spi.ServiceException;

public class PhrException400 extends ServiceException {
    public PhrException400(String errorMessage) {
        super(errorMessage);
    }
}