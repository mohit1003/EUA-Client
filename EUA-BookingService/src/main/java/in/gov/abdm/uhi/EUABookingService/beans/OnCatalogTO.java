package in.gov.abdm.uhi.EUABookingService.beans;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Getter;

/**
 * Describes a DHP-Provider catalog
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class OnCatalogTO {

	private DescriptorTO descriptor;

	private List<CategoryTO> categories;

	private List<OnFulfillmentTO> fulfillments;

	private List<PaymentTO> payments;

	@Getter(lazy = true)
	private final List<ProviderTO> providers = new ArrayList<>();

	/**
	 * Time after which catalog has to be refreshed
	 */
	private String exp;
}
