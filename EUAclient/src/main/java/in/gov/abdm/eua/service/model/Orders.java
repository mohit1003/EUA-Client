package in.gov.abdm.eua.service.model;

import javax.persistence.*;

@Entity
@Table(schema = "eua")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "category_id")
    private Integer categoryId;
    @Column(name = "order_date")
    private String orderDate;
    @Column(name = "healthcare_service_name")
    private String healthcareServiceName;
    @Column(name = "healthcare_service_id")
    private String healthcareServiceId;
    @Column(name = "healthcare_provider_name")
    private String healthcareProviderName;
    @Column(name = "healthcare_provider_id")
    private String healthcareProviderId;
    @Column(name = "healthcare_service_provider_email")
    private String healthcareServiceProviderEmail;
    @Column(name = "healthcare_service_provider_phone")
    private String healthcareServiceProviderPhone;
    @Column(name = "healthcare_professional_name")
    private String healthcareProfessionalName;
    @Column(name = "healthcare_professional_image")
    private String healthcareProfessionalImage;
    @Column(name = "healthcare_professional_email")
    private String healthcareProfessionalEmail;
    @Column(name = "healthcare_professional_phone")
    private String healthcareProfessionalPhone;
    @Column(name = "healthcare_professional_id")
    private String healthcareProfessionalId;
    @Column(name = "healthcare_professional_gender")
    private String healthcareProfessionalGender;
    @Column(name = "service_fulfillment_start_time")
    private String serviceFulfillmentStartTime;
    @Column(name = "service_fulfillment_end_time")
    private String serviceFulfillmentEndTime;
    @Column(name = "service_fulfillment_type")
    private String serviceFulfillmentType;

    private String symptoms;
    @Column(name = "languages_spoken_by_healthcare_professional")
    private String languagesSpokenByHealthcareProfessional;
    @Column(name = "healthcare_professional_experience")
    private String healthcareProfessionalExperience;
    @Column(name = "is_service_fulfilled")
    private String isServiceFulfilled;
    @Column(name = "healthcare_professional_department")
    private String healthcareProfessionalDepartment;

    @ManyToOne
    @JoinColumn(name = "user_id",  referencedColumnName ="user_id")
    private User user;

    @OneToOne
    private Payments payments;

    @OneToOne
    @JoinColumn(name = "transaction_id", referencedColumnName ="transaction_id")
    private Payments payment;

    @OneToOne
    private Payments payment121;


}
