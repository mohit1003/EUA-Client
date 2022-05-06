package in.gov.abdm.uhi.common.dto;

import java.time.LocalDate;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

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

	private String phone;

	private String email;

	private Map<String, String> tags;
}
