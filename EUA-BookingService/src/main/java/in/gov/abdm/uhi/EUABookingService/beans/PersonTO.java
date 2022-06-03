package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

/**
 * Describes a person.
 */
@Data
@JsonInclude(Include.NON_NULL)
public class PersonTO {

	private String id;

	private String name;

	private String image;

	private LocalDate dob;

	private String gender;

	private String cred;

	private Map<String, String> tags;
}
