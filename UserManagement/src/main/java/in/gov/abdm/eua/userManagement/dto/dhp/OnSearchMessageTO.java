package in.gov.abdm.eua.userManagement.dto.dhp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import in.gov.abdm.uhi.common.dto.IntentTO;
import in.gov.abdm.uhi.common.dto.OnCatalogTO;
import in.gov.abdm.uhi.common.dto.OrderTO;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * SearchMessage
 */
@Data
@JsonInclude(Include.NON_NULL)
@NotNull
public class OnSearchMessageTO {
	
	@Valid
	private IntentTO intent;
	@JsonProperty(value = "order_id")
	private String orderId;

	private OrderTO order;

	@Valid
	private OnCatalogTO catalog;
}
