package in.gov.abdm.uhi.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Details on the start of fulfillment
 */
@Data
@JsonInclude(Include.NON_NULL)
public class FulfillmentTimeTO {

	private TimeTO time;

	private DescriptorTO instructions;

	private ContactTO contact;

	private PersonTO person;
}
