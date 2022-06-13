/**
 *
 */
package in.gov.abdm.uhi.Wrapper.dto.phr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author Rajesh
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginPreVerificationResponse extends ServiceResponse {

    private Object transactionId;

    private String mobileEmail;

    private Set<String> mappedPhrAddress;


}
