package in.gov.abdm.eua.client.dto;

import lombok.Data;

@Data
public class AckResponse {
    private MessageResponse message;
    private Error error;
}
