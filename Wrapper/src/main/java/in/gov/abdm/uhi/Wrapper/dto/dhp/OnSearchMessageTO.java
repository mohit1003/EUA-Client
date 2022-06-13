package in.gov.abdm.uhi.Wrapper.dto.dhp;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import in.gov.abdm.uhi.Wrapper.dto.beans.IntentTO;
import in.gov.abdm.uhi.Wrapper.dto.beans.OrderTO;
import in.gov.abdm.uhi.common.dto.OnCatalogTO;
import lombok.Data;

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
    private OrderTO order;
}
