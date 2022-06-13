package in.gov.abdm.uhi.Wrapper.dto.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(value = "fulfillment_id")
    private String fulfillmentid;

    private PriceTO price;


    private ItemQuantityTO quantity;
}
