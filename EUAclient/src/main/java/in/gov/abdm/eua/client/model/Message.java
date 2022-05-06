package in.gov.abdm.eua.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import in.gov.abdm.eua.client.dto.EuaRequestBody;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(schema = "eua")
@Data
public class Message {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, columnDefinition = "text")
    private String messageId;

    @Column(nullable = false, columnDefinition = "text")
    private String consumerId;

    @Column(columnDefinition = "text")
    private String response;


    @Column(nullable = false)
    private String dhpQueryType;

    @Column(nullable = false)
    private String createdAt;

}
