package in.gov.abdm.eua.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import in.gov.abdm.uhi.common.dto.IntentTO;
import in.gov.abdm.uhi.common.dto.OnCatalogTO;
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
	@Valid
	private OnCatalogTO catalog;
}
