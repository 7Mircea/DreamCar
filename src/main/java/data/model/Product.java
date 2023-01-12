package data.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "PRODUCT")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "Product.findAll", query = "SELECT p from Product p"),
        @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p where p.id=:productId")})
public class Product {
    @Id
    @Column
    Integer id;
    @Size(max = 45)
    @Column
    String name;
    @Transient
    boolean selected;
    @OneToOne(targetEntity = Auction.class, mappedBy = "product")
    private Auction auction;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

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
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", selected=" + selected +
                ", auction=" + auction +
                '}';
    }
}
