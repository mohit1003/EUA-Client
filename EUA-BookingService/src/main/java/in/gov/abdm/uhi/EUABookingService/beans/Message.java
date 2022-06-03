package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;

@Data
public class Message {
    @Valid
    private IntentTO intent;
    @JsonProperty(value = "order_id")
    private String orderId;

    private OrderTO order;
    @Valid
    private CatalogTO catalog;
}
