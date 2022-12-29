package data.dao;

import data.model.Characteristic;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

@Named(value = "characteristicDao")
@RequestScoped
public class CharacteristicDao {
    @PersistenceContext
    private EntityManager em;

    public List<Characteristic> getCharacteristicsForProduct(int productId) {
        //TODO implement function
        return Collections.emptyList();
    }

    public void setCharacteristicsForProduct(List<Characteristic> characteristic) {
        this.em.persist(characteristic);
        this.em.flush();
    }

    public void deleteCharacteristic(int productId, int characteristicId) {
        //TODO implement
    }
}
