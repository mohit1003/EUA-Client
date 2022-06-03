package in.gov.abdm.uhi.EUABookingService.beans;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Describes how a single product/service will be rendered/fulfilled to the end
 * customer
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class OnFulfillmentTO {

	private String id;

	private String type;

	@JsonProperty(value = "provider_id")
	private String providerId;

	private String state;

	private Boolean tracking;

	private FulfillmentCustomerTO customer;

	private AgentTO person;

	private ContactTO contact;

	private FulfillmentTimeTO start;

	private FulfillmentTimeTO end;

	private Map<String, String> tags;
}
