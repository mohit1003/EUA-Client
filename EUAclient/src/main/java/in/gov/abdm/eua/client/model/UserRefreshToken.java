package in.gov.abdm.eua.client.model;

import javax.persistence.*;

@Entity
@Table(schema = "eua", name = "user_refreshtoken")
public class UserRefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_refreshtoken_id")
    private Integer userrefreshtokenId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "refresh_token")
    private String refreshToken;

}
