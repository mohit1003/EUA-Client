package in.gov.abdm.eua.service.model;

import javax.persistence.*;

@Entity
@Table(schema = "eua")
public class Categories {
    @Id
    @Column(name = "category_id")
    private Long categoryId;

    private String descriptor;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Orders orders;
}
