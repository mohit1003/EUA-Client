package in.gov.abdm.eua.userManagement.dto.phr;

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
