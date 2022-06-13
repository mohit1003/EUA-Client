package in.gov.abdm.uhi.EUABookingService.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Describes a DHP-Provider catalog
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class CatalogTO {

	private DescriptorTO descriptor;

	private List<CategoryTO> categories;

	private List<FulfillmentTO> fulfillments;

	private List<PaymentTO> payments;

//	@Getter(lazy = true)
	private List<ProviderTO> providers;

	/**
	 * Time after which catalog has to be refreshed
	 */
	private String exp;
}
