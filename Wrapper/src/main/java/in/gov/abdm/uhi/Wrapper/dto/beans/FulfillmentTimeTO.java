package in.gov.abdm.uhi.Wrapper.dto.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Details on the start of fulfillment
 */
@Data
@JsonInclude(Include.NON_NULL)
public class FulfillmentTimeTO {
    @JsonProperty(value = "time")
    private TimeTO time;

//	@JsonProperty(value = "time")
//	private String timeString;

    private DescriptorTO instructions;

    private ContactTO contact;

    private PersonTO person;

    private Location location;
}
