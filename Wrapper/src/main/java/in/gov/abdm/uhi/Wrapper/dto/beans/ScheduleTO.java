package in.gov.abdm.uhi.Wrapper.dto.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

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
