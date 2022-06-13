package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Describes an address
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class AddressTO {

	private String door;

	private String name;

	private String building;

	private String street;

	private String locality;

	private String ward;

	private String city;

	private String state;

	private String country;

	@JsonProperty("full")
	private String fullAddress;

	@JsonProperty(value = "area_code")
	private String areaCode;
}
