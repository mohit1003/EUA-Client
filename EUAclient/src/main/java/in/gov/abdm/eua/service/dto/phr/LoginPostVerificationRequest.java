package in.gov.abdm.eua.service.dto.phr;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * @author mohit-lti
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class LoginPostVerificationRequest {
	@NotNull(message = "transactionId cannot be null")
	private Object transactionId;
	@NotNull(message = "patientId cannot be null")
	private Object patientId;
	@NotNull(message = "requesterId cannot be null")
	private Object requesterId;
}
