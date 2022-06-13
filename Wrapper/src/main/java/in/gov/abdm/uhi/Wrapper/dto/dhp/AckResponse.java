package in.gov.abdm.uhi.Wrapper.dto.dhp;

import lombok.Data;

@Data
public class AckResponse {
    private MessageResponse message;
    private Error error;
}
