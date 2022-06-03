package in.gov.abdm.uhi.EUABookingService.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Describes the description of a real-world object.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class DescriptorTO {

	private String name;

	private String code;

	private String symbol;

	@JsonProperty(value = "short_desc")
	private String shortDesc;

	@JsonProperty(value = "long_desc")
	private String longDesc;

	private List<String> images;

	private String audio;

	@JsonProperty(value = "3d_render")
	private String render3d;
}
