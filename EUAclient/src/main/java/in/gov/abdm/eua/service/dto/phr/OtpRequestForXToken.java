package in.gov.abdm.eua.service.dto.phr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OtpRequestForXToken {
    @NotBlank(message = "Otp cannot be blank")
    private String otp;
    @NotBlank(message = "txnId cannot be blank")
    private String txnId;

}
