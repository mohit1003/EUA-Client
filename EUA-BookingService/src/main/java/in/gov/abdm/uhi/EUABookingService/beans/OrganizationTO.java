package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/**
 * Describes an organization
 */
@Data
@JsonInclude(Include.NON_NULL)
public class OrganizationTO {

	private String name;

	private String cred;
}
