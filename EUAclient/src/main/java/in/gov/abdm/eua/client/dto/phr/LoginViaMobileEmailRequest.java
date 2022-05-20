package in.gov.abdm.eua.client.dto.phr;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Valid
public class LoginViaMobileEmailRequest{
	@NotBlank(message = "value cannot be null/blank")
	@JsonProperty("healthIdNumber")
	@Pattern(regexp="[0-9]{2}-[0-9]{4}-[0-9]{4}-[0-9]{4}", message = "invalid value/healthIdNumber")
	private String value;
	@NotBlank(message = "purpose cannot be null/blank")
	private String purpose;
	@NotBlank(message = "authMode cannot be null/blank")
	private String authMode;
	@NotNull(message = "requester cannot be null/blank")
	@Valid
	private Requester requester;

}
