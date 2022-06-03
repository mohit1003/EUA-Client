package in.gov.abdm.eua.service.model;

import javax.persistence.*;

@Entity
@Table(schema = "eua", name = "user_refreshtoken")
public class UserRefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_refreshtoken_id")
    private Long userrefreshtokenId;
    @Column(name = "refresh_token")
    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName ="user_id" )
    private User user;

}
