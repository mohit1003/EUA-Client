package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Describes the details of an order
 */
@Data
@JsonInclude(Include.NON_NULL)
public class OrderTO {

	private String id;

	private String state;

	private ProviderTO provider;

	private List<OrderItemsTO> items;

	private OrderItemsTO item;

	private BillingTO billing;

	private FulfillmentTO fulfillment;

	private QuotationTO quote;

	private PaymentTO payment;

	private AgentTO customer;

	@JsonProperty(value = "created_at")
	private String createdAt;

	@JsonProperty(value = "updated_at")
	private String updatedAt;
}
