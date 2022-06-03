package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/**
 * indicates language code. DHP supports country codes as per ISO 639.2 standard
 */
@Data
@JsonInclude(Include.NON_NULL)
public class LanguageTO {

	private String code;
}
