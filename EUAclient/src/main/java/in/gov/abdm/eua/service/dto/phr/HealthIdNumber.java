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
public class HealthIdNumber {
    @NotBlank(message = "authMethod cannot be blank")
    private String authMethod;
    @NotBlank(message = "healthId cannot be blank")
    private String healthid;
}
