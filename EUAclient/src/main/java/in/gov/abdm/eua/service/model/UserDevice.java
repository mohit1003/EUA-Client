package in.gov.abdm.eua.service.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_device", schema = "eua")
public class UserDevice {
    @Id
    @Column(name = "mac_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long macId;
    @Column(name = "device_name")
    private String deviceName;
    @Column(name = "device_type")
    private String deviceType;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName ="user_id")
    private User user;

    @OneToMany(mappedBy = "userDevice")
    private Set<Message> messages;
}
