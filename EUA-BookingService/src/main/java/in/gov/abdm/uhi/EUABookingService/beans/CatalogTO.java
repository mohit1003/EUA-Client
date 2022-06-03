package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Describes a DHP-Provider catalog
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
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
