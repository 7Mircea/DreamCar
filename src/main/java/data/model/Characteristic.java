package data.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "CHARACTERISTIC")
@XmlRootElement
@NamedQueries({@NamedQuery(name="Characteristic.findAll",query = "SELECT c from Characteristic c"),
        @NamedQuery(name="Characteristic.findCharacteristicForProduct",query = "SELECT c from Characteristic c where c.productId = :productId")})
public class Characteristic {
    @Id
    @Column(name = "product_id")
    Integer productId;
    @Id
    @Column(name = "characteristic_id")
    Integer characteristicId;

    String name;
    @Size(max = 100)
    String value;


    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCharacteristicId() {
        return characteristicId;
    }

    public void setCharacteristicId(Integer characteristicId) {
        this.characteristicId = characteristicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
