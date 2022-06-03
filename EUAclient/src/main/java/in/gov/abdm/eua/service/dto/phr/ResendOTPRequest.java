package in.gov.abdm.eua.service.dto.phr;

import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ResendOTPRequest {

	@NotNull(message = "sessionId Cannot be null")
	private Object sessionId;

}