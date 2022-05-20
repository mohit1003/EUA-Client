package in.gov.abdm.eua.client.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "eua")
public class Payments {
    @Id
    @Column(name = "transaction_id")
    private Integer transactionId;
    @Column(name = "order_id")
    private Integer orderId;
    @Column(name = "user_id")
    private Integer userId;
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
}
