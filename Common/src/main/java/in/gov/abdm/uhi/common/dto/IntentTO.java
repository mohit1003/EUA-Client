package in.gov.abdm.uhi.common.dto;

import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Intent of a user. Used for searching for services
 */
@Data
@JsonInclude(Include.NON_NULL)
@NotNull
public class IntentTO {
	
	private DescriptorTO descriptor;
	
	private ProviderTO provider;

	private FulfillmentTO fulfillment;

	private PaymentTO payment;

	private CategoryTO category;

	private ItemTO item;

	private Map<String, String> tags;
}
