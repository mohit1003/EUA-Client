package in.gov.abdm.uhi.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Describes a city
 */
@Data
@JsonInclude(Include.NON_NULL)
public class CityTO {

	private String name;

	private String code;
}
