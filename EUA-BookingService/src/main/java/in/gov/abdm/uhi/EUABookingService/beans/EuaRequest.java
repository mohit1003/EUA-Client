package in.gov.abdm.uhi.EUABookingService.beans;

import lombok.Data;

@Data
public class EuaRequest {
    private ContextTO context;
    private Message message;
}
