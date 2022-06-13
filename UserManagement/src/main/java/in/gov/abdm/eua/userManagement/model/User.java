package in.gov.abdm.eua.userManagement.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(schema = "eua")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "health_id_number")
    private String healthIdNumber;
    @Column(name = "health_id")
    private String healthId;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "mobile")
    private String mobile;

    @Column(name = "day_of_birth")
    private String dayOfBirth;

    @Column(name = "month_of_birth")
    private String monthOfBirth;

    @Column(name = "year_of_birth")
    private String yearOfBirth;

    @Column(name = "profile_photo", length = 20000)
    private String profilePhoto;

    @Column(name = "aadhar_verified")
    private String aadhaarVerified;

    @Column(name = "kyc_photo", length = 20000)
    private String kycPhoto;

    @Column(name = "is_kyc_verified")
    private Boolean kycVerified;

    @Column(name = "is_email_verified")
    private Boolean emailVerified;

    @Column(name = "is_mobile_verified")
    private Boolean mobileVerified;

    @Column(name = "verification_type")
    private String verificationType;

    @Column(name = "verification_status")
    private String verificationStatus;


    @ElementCollection
    private Set<String> authMethods;


    @OneToMany(mappedBy = "user")
    private Set<Orders> orders;

    @OneToMany(mappedBy = "user")
    private Set<Payments> payments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserRefreshToken> userRefreshTokens;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserAbhaAddress> user_abhaAddresses;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Address> addresses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserDevice> userDevices;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Message> messages;

}
