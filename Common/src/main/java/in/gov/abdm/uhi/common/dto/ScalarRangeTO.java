package in.gov.abdm.uhi.common.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * ScalarRange
 */
@Data
@JsonInclude(Include.NON_NULL)
public class ScalarRangeTO {

	private BigDecimal min;

	private BigDecimal max;
}
