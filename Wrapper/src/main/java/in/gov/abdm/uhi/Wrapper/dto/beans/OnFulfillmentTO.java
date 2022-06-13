package in.gov.abdm.uhi.Wrapper.dto.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * Describes how a single product/service will be rendered/fulfilled to the end
 * customer
 */
@Data
@JsonInclude(Include.NON_NULL)
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
