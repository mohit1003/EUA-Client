package in.gov.abdm.uhi.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * ItemQuantityAllocated
 */
@Data
@JsonInclude(Include.NON_NULL)
public class ItemQuantityCountTO {

	private String count;

	private ScalarTO measure;

	private String available;
}
