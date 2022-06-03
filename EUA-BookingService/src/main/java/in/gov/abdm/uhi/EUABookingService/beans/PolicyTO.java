package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Describes a policy. Allows for domain extension.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class PolicyTO {

	private String id;

	private DescriptorTO descriptor;

	@JsonProperty(value = "parent_policy_id")
	private String parentPolicyId;

	private TimeTO time;
}
