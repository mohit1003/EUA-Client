package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * OrderItems
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class OrderItemsTO {

	private String id;

	private DescriptorTO descriptor;
	private String fulfillment_id;
	private PriceTO price;

	private ItemQuantityTO quantity;
}
