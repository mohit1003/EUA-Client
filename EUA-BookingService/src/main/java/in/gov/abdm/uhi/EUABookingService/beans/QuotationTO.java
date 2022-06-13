package in.gov.abdm.uhi.EUABookingService.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Describes a quote
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class QuotationTO {

	private PriceTO price;

	private List<QuotationBreakupTO> breakup;

	private String ttl;
}
