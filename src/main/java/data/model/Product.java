package data.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PRODUCT")
public class Product {
    @Id
    @Column
    Integer id;
    @Size(max = 45)
    @Column
    String name;

    @OneToOne(targetEntity = Auction.class, mappedBy = "product")
    private Auction auction;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
