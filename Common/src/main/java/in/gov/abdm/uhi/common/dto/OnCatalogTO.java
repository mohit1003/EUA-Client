package in.gov.abdm.uhi.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes a DHP-Provider catalog
 */
@Data
@JsonInclude(Include.NON_NULL)
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
