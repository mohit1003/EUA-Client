<<<<<<< HEAD
<<<<<<< HEAD:UserManagement/src/main/java/in/gov/abdm/eua/userManagement/dto/dhp/MessageTO.java
package in.gov.abdm.eua.userManagement.dto.dhp;
=======
package in.gov.abdm.eua.service.dto.dhp;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a:EUAclient/src/main/java/in/gov/abdm/eua/service/dto/dhp/MessageTO.java
=======
package in.gov.abdm.eua.userManagement.dto.dhp;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageTO {
    private Long id;

    @JsonProperty("message_id")
    private String messageId;

    @JsonProperty("consumer_id")
    private String consumerId;

    @JsonProperty("response")
    private String response;


    @JsonProperty("dhp_query_type")
    private String dhpQueryType;

    @JsonProperty("created_at")
    private String createdAt;
}
