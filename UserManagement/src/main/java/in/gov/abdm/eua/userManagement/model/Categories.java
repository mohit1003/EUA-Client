package in.gov.abdm.eua.userManagement.model;

import javax.persistence.*;

@Entity
@Table(schema = "eua", name = "categories")
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Long categoryId;

    private String descriptor;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private  Orders orders;
}
