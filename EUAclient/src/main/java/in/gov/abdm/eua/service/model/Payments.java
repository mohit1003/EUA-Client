package in.gov.abdm.eua.service.model;

import javax.persistence.*;

@Entity
@Table(schema = "eua")
public class Payments {
    @Id
    @Column(name = "transaction_id")
    private Long transactionId;
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

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName ="user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName ="order_id")
    private Orders order;

    @OneToOne
    private Orders orders;

}
