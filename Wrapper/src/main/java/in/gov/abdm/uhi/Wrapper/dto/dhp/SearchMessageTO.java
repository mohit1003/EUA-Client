package in.gov.abdm.uhi.Wrapper.dto.dhp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import in.gov.abdm.uhi.Wrapper.dto.beans.CatalogTO;
import in.gov.abdm.uhi.Wrapper.dto.beans.IntentTO;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * SearchMessage
 */
@Data
@JsonInclude(Include.NON_NULL)
@NotNull
public class SearchMessageTO {

    @Valid
    private IntentTO intent;
    @Valid
    private CatalogTO catalog;
}
