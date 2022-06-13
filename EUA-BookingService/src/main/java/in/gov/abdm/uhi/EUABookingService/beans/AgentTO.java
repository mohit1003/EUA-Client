package in.gov.abdm.uhi.EUABookingService.beans;

import java.time.LocalDate;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Describes an order executor
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class AgentTO {

	private String id;

	private String name="";

	private String image="";

	private LocalDate dob;

	private String gender="";

	private String cred;

	private PersonTO person;

	private String phone="";

	private String email="";

	private Map<String, Object> tags;
}
