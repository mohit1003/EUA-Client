package in.gov.abdm.uhi.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * QuotationBreakup
 */
@Data
@JsonInclude(Include.NON_NULL)
public class QuotationBreakupTO {

	private String title;

	private PriceTO price;
}
