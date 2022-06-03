package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/**
 * TimeRange
 */
@Data
@JsonInclude(Include.NON_NULL)
public class TimeRangeTO {

	private String start;

	private String end;
}
