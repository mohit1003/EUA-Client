package in.gov.abdm.eua.client.model;

import javax.persistence.*;

@Entity
@Table(schema = "eua")
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Integer categoryId;

    private String descriptor;
}
