package in.gov.abdm.uhi.EUABookingService.beans;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Describes an item. Allows for domain extension.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class ItemTO {

	private String id;

	@JsonProperty(value = "parent_item_id")
	private String parentItemId;

	private DescriptorTO descriptor;

	private PriceTO price;

	@JsonProperty(value = "category_id")
	private String categoryId;

	@JsonProperty(value = "fulfillment_id")
	private String fulfillmentId;

	private TimeTO time;

	private Boolean matched;

	private Boolean related;

	private Boolean recommended;

	private ItemQuantityTO quantity;

	private Map<String, String> tags;
}
