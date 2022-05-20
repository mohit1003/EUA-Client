package in.gov.abdm.eua.client.dto.phr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class to represent JWT token as response.
 * 
 * @author Ashok Kumar<ashok@parserlabs.com>
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class JwtResponse extends ServiceResponse {


	private String token;

	long expiresIn;

	private String refreshToken;

	long refreshExpiresIn;

	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private String phrAdress;

	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private String firstName;
}