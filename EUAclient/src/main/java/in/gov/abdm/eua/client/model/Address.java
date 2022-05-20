package in.gov.abdm.eua.client.model;

import javax.persistence.*;

@Entity
@Table(schema = "eua")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer addressId;
    private String door;
    private String building;
    private String street;
    @Column(name = "area_code")
    private String areaCode;
}
