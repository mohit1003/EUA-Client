package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Details on the start of fulfillment
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class FulfillmentTimeTO {
	@JsonProperty(value = "time")
	private TimeTO time;

	private DescriptorTO instructions;

	private ContactTO contact;

	private PersonTO person;

	private Location location;
}
