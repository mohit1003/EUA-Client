package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/**
 * OrderItems
 */
@Data
@JsonInclude(Include.NON_NULL)
public class OrderItemsTO {

	private String id;

	private DescriptorTO descriptor;
	private String fulfillment_id;
	private PriceTO price;

	private ItemQuantityTO quantity;
}
