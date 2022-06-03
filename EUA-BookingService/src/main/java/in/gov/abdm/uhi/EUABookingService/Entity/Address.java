package in.gov.abdm.uhi.EUABookingService.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(schema = "eua")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private Long addressId;
    private String stateCode;
    private String stateName;
    private String districtCode;
    private String districtName;
    private String countryCode;
    private String townName;
    private String townCode;
    private String subdistrictName;
    private String subDistrictCode;
    private String wardName;
    private String wardCode;
    private String villageName;
    private String villageCode;
    private String pincode;
    private String address;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
