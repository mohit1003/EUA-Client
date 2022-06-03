package in.gov.abdm.uhi.Wrapper.dto.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Getter
@Setter
public class EuaRequest {

    private ContextTO context;
    private Message message;

}
