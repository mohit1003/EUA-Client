package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * An object representing a scalar quantity.
 */
@Data
@JsonInclude(Include.NON_NULL)
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
