package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Describes a state
 */
@Data
@JsonInclude(Include.NON_NULL)
public class StateTO {

	private DescriptorTO descriptor;

	@JsonProperty(value = "updated_at")
	private String updatedAt;

	@JsonProperty(value = "updated_by")
	private String updatedBy;
}
