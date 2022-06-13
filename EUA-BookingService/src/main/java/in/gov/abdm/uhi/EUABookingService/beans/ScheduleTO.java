package in.gov.abdm.uhi.EUABookingService.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Describes a schedule
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class ScheduleTO {

	private String frequency;

	private List<String> holidays;

	private List<String> times;
}
