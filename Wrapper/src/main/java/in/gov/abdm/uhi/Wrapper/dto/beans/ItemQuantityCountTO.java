package in.gov.abdm.uhi.Wrapper.dto.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * ItemQuantityAllocated
 */
@Data
@JsonInclude(Include.NON_NULL)
public class ItemQuantityCountTO {

    private Integer count;

    private ScalarTO measure;
}
