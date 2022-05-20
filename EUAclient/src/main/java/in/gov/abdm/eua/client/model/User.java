package in.gov.abdm.eua.client.model;

import javax.persistence.*;

@Entity
@Table(schema = "eua")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int userId;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "abha_id")
    private String abhaId;
    @Column(name = "email")
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "mobile")
    private String mobile;

}
