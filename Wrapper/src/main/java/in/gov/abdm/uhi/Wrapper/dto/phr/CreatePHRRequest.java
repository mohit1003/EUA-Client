package in.gov.abdm.uhi.Wrapper.dto.phr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CreatePHRRequest {
    @NotNull(message = "SessionId is mandatory")
    private Object sessionId;
    @NotNull(message = "phrAddress is mandatory")
    private Object phrAddress;

    //	@Encryption(required = false)
//	@Password(required = false)
    private Object password;

    private Boolean isAlreadyExistedPHR;

}