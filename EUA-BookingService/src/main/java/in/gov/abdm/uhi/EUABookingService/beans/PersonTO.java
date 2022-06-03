package in.gov.abdm.uhi.EUABookingService.beans;

import java.time.LocalDate;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Describes a person.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class PersonTO {

	private String id;

	private String name;

	private String image;

	private LocalDate dob;

	private String gender;

	private String cred;

	private Map<String, String> tags;
}
