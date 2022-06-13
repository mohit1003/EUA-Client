package in.gov.abdm.uhi.Wrapper.dto.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

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
