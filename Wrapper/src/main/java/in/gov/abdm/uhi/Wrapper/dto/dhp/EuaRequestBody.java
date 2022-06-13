package in.gov.abdm.uhi.Wrapper.dto.dhp;


import in.gov.abdm.uhi.Wrapper.dto.beans.ContextTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EuaRequestBody {
    private ContextTO context;
    private SearchMessageTO message;
}
