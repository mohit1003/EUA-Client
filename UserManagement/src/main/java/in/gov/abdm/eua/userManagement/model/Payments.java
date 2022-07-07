<<<<<<< HEAD
<<<<<<< HEAD:UserManagement/src/main/java/in/gov/abdm/eua/userManagement/model/Payments.java
package in.gov.abdm.eua.userManagement.model;
=======
package in.gov.abdm.uhi.EUABookingService.Entity;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a:EUA-BookingService/src/main/java/in/gov/abdm/uhi/EUABookingService/Entity/Payments.java
=======
package in.gov.abdm.eua.userManagement.model;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(schema = "eua")
@Data
public class Payments {
    @Id
    @Column(name = "transaction_id")
    private String transactionId;
    private String method;
    private String currency;
    @Column( name = "transaction_time_stamp")
    private String transactionTimestamp;
    @Column(name = "consultation_charge")
    private String consultationCharge;
    @Column(name = "phr_handling_fees")
    private String phrHandlingFees;
    private String sgst;
    private String cgst;
    @Column(name = "transaction_state")
    private String transactionState;

    @Column(name = "healthIdNumber")
    private String UserAbhaId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName ="user_id")
    private User user;

}
