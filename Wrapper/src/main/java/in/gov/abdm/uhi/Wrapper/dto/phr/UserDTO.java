package in.gov.abdm.uhi.Wrapper.dto.phr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private Long userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String abhaId;
    private String email;
    private String password;
    private String mobile;
    private String dateOfBirth;
    private String monthOfBirth;
    private String yearOfBirth;
    private String address;
    private String stateCode;
    private String districtCode;
    private String pinCode;
    private String countryCode;
    private String gender;
    private String profilePhoto;
    private String aadhaarVerified;
    private String kycPhoto;
    private Boolean kycVerified;
    private Boolean emailVerified;
    private Boolean mobileVerified;
    private Set<String> linkedPhrAddress;
}
