package in.gov.abdm.uhi.EUABookingService.beans;

import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Intent of a user. Used for searching for services
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
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
