package in.gov.abdm.uhi.Wrapper.dto.beans;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Getter
@Setter
public class Message {

    @Valid
    private IntentTO intent;
    @JsonProperty(value = "order_id")
    private String orderId;

    private OrderTO order;
    @Valid
    private CatalogTO catalog;
}
