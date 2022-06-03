package in.gov.abdm.uhi.common.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Describes how a single product/service will be rendered/fulfilled to the end
 * customer
 */
@Data
@JsonInclude(Include.NON_NULL)
public class FulfillmentTO {

	private String id;

	private String type;

	@JsonProperty(value = "provider_id")
	private String providerId;

	private StateTO state;

	private Boolean tracking;

	private FulfillmentCustomerTO customer;

	private AgentTO person;

	private AgentTO agent;

	private ContactTO contact;

	private FulfillmentTimeTO start;

	private FulfillmentTimeTO end;

	private Map<String, String> tags;
}
