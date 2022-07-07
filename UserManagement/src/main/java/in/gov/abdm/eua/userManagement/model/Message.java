<<<<<<< HEAD
<<<<<<< HEAD:UserManagement/src/main/java/in/gov/abdm/eua/userManagement/model/Message.java
package in.gov.abdm.eua.userManagement.model;
=======
package in.gov.abdm.eua.service.model;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a:EUAclient/src/main/java/in/gov/abdm/eua/service/model/Message.java
=======
package in.gov.abdm.eua.userManagement.model;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(schema = "eua")
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
