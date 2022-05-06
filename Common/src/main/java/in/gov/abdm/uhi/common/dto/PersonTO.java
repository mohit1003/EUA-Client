package in.gov.abdm.uhi.common.dto;

import java.time.LocalDate;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

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
