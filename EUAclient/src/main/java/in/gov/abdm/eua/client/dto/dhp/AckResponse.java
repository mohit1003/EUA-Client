package in.gov.abdm.eua.client.dto.dhp;

import lombok.Data;

@Data
public class AckResponse {
    private MessageResponse message;
    private Error error;
}
