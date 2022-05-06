package in.gov.abdm.uhi.common.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

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
