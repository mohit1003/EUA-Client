<<<<<<< HEAD
<<<<<<< HEAD:UserManagement/src/main/java/in/gov/abdm/eua/userManagement/dto/phr/RegistrationByMobileOrEmailRequest.java
package in.gov.abdm.eua.userManagement.dto.phr;
=======
package in.gov.abdm.eua.service.dto.phr;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a:EUAclient/src/main/java/in/gov/abdm/eua/service/dto/phr/RegistrationByMobileOrEmailRequest.java
=======
package in.gov.abdm.eua.userManagement.dto.phr;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegistrationByMobileOrEmailRequest extends UserData {
    private Object sessionId;
    private NamePhrRegistration name;
    private DateOfBirthRegistrationPhr dateOfBirth;
    private String gender;
    private String stateCode;
    private String districtCode;
    private String email;
    private String mobile;
    private String countryCode;
    private String pinCode;
    private String address;


    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NamePhrRegistration {
        private String first;
        private String middle;
        private String last;
    }

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DateOfBirthRegistrationPhr {
        private String date;
        private String month;
        private String year;
    }
}
