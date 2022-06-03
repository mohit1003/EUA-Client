package in.gov.abdm.eua.service.dto.phr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResendLoginOTPRequest {
	private String txnId;

	private String authMethod;


}
