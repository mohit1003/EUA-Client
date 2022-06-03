package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.math.BigDecimal;

/**
 * ScalarRange
 */
@Data
@JsonInclude(Include.NON_NULL)
public class ScalarRangeTO {

	private BigDecimal min;

	private BigDecimal max;
}
