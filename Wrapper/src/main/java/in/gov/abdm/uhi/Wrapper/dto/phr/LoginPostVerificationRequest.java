package in.gov.abdm.uhi.Wrapper.dto.phr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * @author mohit-lti
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
