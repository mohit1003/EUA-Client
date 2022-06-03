package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.util.List;

/**
 * Describes a schedule
 */
@Data
@JsonInclude(Include.NON_NULL)
public class ScheduleTO {

	private String frequency;

	private List<String> holidays;

	private List<String> times;
}
