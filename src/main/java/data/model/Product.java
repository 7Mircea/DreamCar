package data.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "PRODUCT")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "Product.findAll", query = "SELECT p from Product p"),
        @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p where p.id=:productId"),
@NamedQuery(name = "Product.findAllProductIds",query = "SELECT p.id FROM Product p")})
public class Product {
    @Id
    @Column
    Integer id;
    @Size(min = 2,max = 45)
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

    @Override
    public String toString() {
        return name;
    }
}
