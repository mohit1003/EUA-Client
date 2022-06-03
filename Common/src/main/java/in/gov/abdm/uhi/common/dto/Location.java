package in.gov.abdm.uhi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private String id;
    private String gps;
    private AddressTO address;
}
