package in.gov.abdm.eua.userManagement.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "eua", name = "message")
@Getter
@Setter
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

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "mac_id", referencedColumnName = "mac_id")
    private UserDevice userDevice;

}
