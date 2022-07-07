<<<<<<< HEAD
<<<<<<< HEAD:UserManagement/src/main/java/in/gov/abdm/eua/userManagement/model/UserRefreshToken.java
package in.gov.abdm.eua.userManagement.model;

import lombok.Data;
=======
package in.gov.abdm.eua.service.model;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a:EUAclient/src/main/java/in/gov/abdm/eua/service/model/UserRefreshToken.java
=======
package in.gov.abdm.eua.userManagement.model;

import lombok.Data;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a

import javax.persistence.*;

@Entity
@Table(schema = "eua", name = "user_refreshtoken")
@Data
public class UserRefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_refreshtoken_id")
    private Long userrefreshtokenId;
    @Column(name = "refresh_token")
    private String refreshToken;

    @ManyToOne
<<<<<<< HEAD
<<<<<<< HEAD:UserManagement/src/main/java/in/gov/abdm/eua/userManagement/model/UserRefreshToken.java
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
=======
    @JoinColumn(name = "user_id", referencedColumnName ="user_id" )
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a:EUAclient/src/main/java/in/gov/abdm/eua/service/model/UserRefreshToken.java
=======
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a
    private User user;

}
