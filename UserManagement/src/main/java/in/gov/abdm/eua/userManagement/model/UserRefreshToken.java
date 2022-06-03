package in.gov.abdm.eua.userManagement.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "eua", name = "user_refreshtoken")
@Getter
@Setter
public class UserRefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_refreshtoken_id")
    private Long userrefreshtokenId;
    @Column(name = "refresh_token")
    private String refreshToken;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName ="user_id" )
    private User user;

}
