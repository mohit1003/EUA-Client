package in.gov.abdm.eua.client.dto;


import in.gov.abdm.eua.client.beans.Context;
import in.gov.abdm.eua.client.beans.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EuaRequestBody {
    private Context context;
    private Message message;
}
