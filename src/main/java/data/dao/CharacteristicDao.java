package data.dao;

import data.model.Characteristic;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;

@Named(value = "characteristicDao")
@RequestScoped
public class CharacteristicDao {
    @PersistenceContext
    private EntityManager em;

    public List<Characteristic> getCharacteristicsForProduct(int productId) {
        return this.em.createNamedQuery("Characteristic.findCharacteristicForProduct", Characteristic.class).setParameter("productId",productId).getResultList();
    }

    public void addAll(List<Characteristic> characteristic) {
        characteristic.stream().parallel().forEach(characteristic1 -> this.em.persist(characteristic));
        this.em.flush();
    }

    public void deleteCharacteristic(int productId, int characteristicId) {
        //TODO implement
    }
}
