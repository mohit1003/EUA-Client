package in.gov.abdm.uhi.EUABookingService.beans;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * ScalarRange
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class ScalarRangeTO {

	private BigDecimal min;

	private BigDecimal max;
}
