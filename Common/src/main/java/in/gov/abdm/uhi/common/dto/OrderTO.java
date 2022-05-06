package in.gov.abdm.uhi.common.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Describes the details of an order
 */
@Data
@JsonInclude(Include.NON_NULL)
public class OrderTO {

	private String id;

	private String state;

	private String provider;

	private List<OrderItemsTO> items;

	private BillingTO billing;

	private FulfillmentTO fulfillment;

	private QuotationTO quote;

	private PaymentTO payment;

	@JsonProperty(value = "created_at")
	private String createdAt;

	@JsonProperty(value = "updated_at")
	private String updatedAt;
}
