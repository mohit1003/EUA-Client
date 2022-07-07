<<<<<<< HEAD
<<<<<<< HEAD:UserManagement/src/main/java/in/gov/abdm/eua/userManagement/exceptions/PhrException500.java
package in.gov.abdm.eua.userManagement.exceptions;
=======
package in.gov.abdm.uhi.Wrapper.exceptions;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a:Wrapper/src/main/java/in/gov/abdm/uhi/Wrapper/exceptions/PhrException500.java
=======
package in.gov.abdm.eua.userManagement.exceptions;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a

import org.hibernate.service.spi.ServiceException;

public class PhrException500 extends ServiceException {
    public PhrException500(String errorMessage) {
        super(errorMessage);
    }
}