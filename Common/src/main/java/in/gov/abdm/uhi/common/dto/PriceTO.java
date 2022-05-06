package in.gov.abdm.uhi.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Describes the price of an item. Allows for domain extension.
 */
@Data
@JsonInclude(Include.NON_NULL)
public class PriceTO {

	private String currency;

	private String value;

	@JsonProperty(value = "estimated_value")
	private String estimatedValue;

	@JsonProperty(value = "computed_value")
	private String computedValue;

	@JsonProperty(value = "listed_value")
	private String listedValue;

	@JsonProperty(value = "offered_value")
	private String offeredValue;

	@JsonProperty(value = "minimum_value")
	private String minimumValue;

	@JsonProperty(value = "maximum_value")
	private String maximumValue;
}
