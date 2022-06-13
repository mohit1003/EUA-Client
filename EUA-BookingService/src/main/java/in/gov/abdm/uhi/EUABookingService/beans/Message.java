package in.gov.abdm.uhi.EUABookingService.beans;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Message {
    @Valid
    private IntentTO intent;
    @JsonProperty(value = "order_id")
    private String orderId;

    private OrderTO order;
    @Valid
    private CatalogTO catalog;
}
