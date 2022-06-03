package in.gov.abdm.uhi.EUABookingService.beans;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * An object representing a scalar quantity.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class ScalarTO {

	private String type;

	private BigDecimal value;

	@JsonProperty(value = "estimated_value")
	private BigDecimal estimatedValue;

	@JsonProperty(value = "computed_value")
	private BigDecimal computedValue;

	private ScalarRangeTO range;

	private String unit;
}
