package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.util.Map;

/**
 * Contact
 */
@Data
@JsonInclude(Include.NON_NULL)
public class ContactTO {

	private String phone;

	private String email;

	private Map<String, String> tags;
}
