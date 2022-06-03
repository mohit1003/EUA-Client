package in.gov.abdm.eua.userManagement.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "eua", name = "user_abha_address")
@Getter
@Setter
public class UserAbhaAddress {
    @Column(name = "user_abha_address_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userPhrAddressId;
    @Column(name = "phr_address")
    private String phrAddress;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName ="user_id")
    private User user;


}
