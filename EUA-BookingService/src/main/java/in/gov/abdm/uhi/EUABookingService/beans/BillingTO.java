package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Describes a billing event
 */
@Data
@JsonInclude(Include.NON_NULL)
public class BillingTO {

	private String name;

	private OrganizationTO organization;

	private AddressTO address;

	private String email;

	private String phone;

	private TimeTO time;

	@JsonProperty(value = "tax_number")
	private String taxNumber;

	@JsonProperty(value = "created_at")
	private String createdAt;

	@JsonProperty(value = "updated_at")
	private String updatedAt;
}
