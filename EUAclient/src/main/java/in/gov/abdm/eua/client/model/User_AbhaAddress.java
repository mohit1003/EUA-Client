package in.gov.abdm.eua.client.model;

import javax.persistence.*;
@Entity
@Table(schema = "eua")
public class User_AbhaAddress {
    @Column(name = "user_abha_address_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userAbhaAddressId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "abha_address")
    private String abhaAddress;
}
