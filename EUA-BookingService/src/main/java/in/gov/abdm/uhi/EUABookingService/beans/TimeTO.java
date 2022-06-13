package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Describes time in its various forms. It can be a single point in time;
 * duration; or a structured timetable of operations
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class TimeTO {

	private String label;

	private String timestamp;

	private String duration;

	private TimeRangeTO range;

	private String days;

	private ScheduleTO schedule;
}
