package in.gov.abdm.eua.service.dto.phr.login;

import in.gov.abdm.eua.service.constants.ConstantsUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchByHealthIdNumberRequest {

    @NotNull(message = "healthIdNumber cannot be null")
    private Object healthIdNumber;
    @NotNull(message = "yearOfBirth")
    private Object yearOfBirth;

}
