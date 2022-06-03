package in.gov.abdm.eua.service.dto.dhp;

import in.gov.abdm.uhi.common.dto.MessageTO;
import in.gov.abdm.uhi.common.dto.ErrorTO;
import lombok.Data;

@Data
public class AckResponse {
    private MessageTO message;
    private ErrorTO error;
}
