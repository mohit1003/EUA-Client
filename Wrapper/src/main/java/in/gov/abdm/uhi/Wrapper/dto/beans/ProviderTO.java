package in.gov.abdm.uhi.Wrapper.dto.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;

/**
 * Describes a service provider. This can be a restaurant, a hospital, a Store
 * etc
 */
@Data
@JsonInclude(Include.NON_NULL)
public class ProviderTO {

    private String id;

    private DescriptorTO descriptor;

    private Set<Location> locations;

    @JsonProperty(value = "category_id")
    private String categoryId;

    private TimeTO time;

    //	@Getter(lazy = true)
    private List<CategoryTO> categories;

    //	@Getter(lazy = true)
    private List<FulfillmentTO> fulfillments;

    //	@Getter(lazy = true)
    private List<PaymentTO> payments;

    //	@Getter(lazy = true)
    private List<ItemTO> items;

    private String exp;

    private BillingTO billing;

    private AgentTO customer;

    private Map<String, String> tags;
}
