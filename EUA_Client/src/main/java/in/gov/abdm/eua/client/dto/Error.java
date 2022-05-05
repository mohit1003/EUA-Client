package in.gov.abdm.eua.client.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Error {
    public String type;
    public String code;
    public String path;
    public String message;
}
