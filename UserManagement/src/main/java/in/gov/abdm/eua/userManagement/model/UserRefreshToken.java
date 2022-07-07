package in.gov.abdm.eua.userManagement.model;

import lombok.Data;

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
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

}
