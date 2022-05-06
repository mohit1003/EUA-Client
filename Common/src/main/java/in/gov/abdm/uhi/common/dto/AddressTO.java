package in.gov.abdm.uhi.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Describes an address
 */
@Data
@JsonInclude(Include.NON_NULL)
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

	@JsonProperty(value = "area_code")
	private String areaCode;
}
