package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.util.List;

/**
 * Describes a quote
 */
@Data
@JsonInclude(Include.NON_NULL)
public class QuotationTO {

	private PriceTO price;

	private List<QuotationBreakupTO> breakup;

	private String ttl;
}
