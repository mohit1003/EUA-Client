package in.gov.abdm.uhi.EUABookingService.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(schema = "eua")
@Data
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
