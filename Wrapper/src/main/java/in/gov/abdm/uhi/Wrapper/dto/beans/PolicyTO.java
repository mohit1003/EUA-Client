package in.gov.abdm.uhi.Wrapper.dto.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Describes a policy. Allows for domain extension.
 */
@Data
@JsonInclude(Include.NON_NULL)
public class PolicyTO {

    private String id;

    private DescriptorTO descriptor;

    @JsonProperty(value = "parent_policy_id")
    private String parentPolicyId;

    private TimeTO time;
}
