package in.gov.abdm.uhi.Wrapper.dto.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Describes a payment
 */
@Data
@JsonInclude(Include.NON_NULL)
public class PaymentTO {

    private String uri;

    @JsonProperty(value = "tl_method")
    private String tlMethod;

    private ParamTO params;

    private String type;

    private String status;

    private TimeTO time;
}
