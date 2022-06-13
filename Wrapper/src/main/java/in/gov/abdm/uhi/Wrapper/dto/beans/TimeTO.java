package in.gov.abdm.uhi.Wrapper.dto.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Describes time in its various forms. It can be a single point in time;
 * duration; or a structured timetable of operations
 */
@Data
@JsonInclude(Include.NON_NULL)
public class TimeTO {

    private String label;

    private String timestamp;

    private String duration;

    private TimeRangeTO range;

    private String days;

    private ScheduleTO schedule;
}
