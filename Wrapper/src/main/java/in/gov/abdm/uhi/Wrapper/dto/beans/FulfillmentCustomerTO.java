package in.gov.abdm.uhi.Wrapper.dto.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * FulfillmentCustomer
 */
@Data
@JsonInclude(Include.NON_NULL)
public class FulfillmentCustomerTO {

    private PersonTO person;

    private ContactTO contact;
}
