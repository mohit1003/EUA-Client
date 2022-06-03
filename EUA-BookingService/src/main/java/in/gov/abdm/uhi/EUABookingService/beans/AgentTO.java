package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

/**
 * Describes an order executor
 */
@Data
@JsonInclude(Include.NON_NULL)
public class AgentTO {

	private String id;

	private String name;

	private String image;

	private LocalDate dob;

	private String gender;

	private String cred;

	private PersonTO person;

	private String phone;

	private String email;

	private Map<String, Object> tags;
}
