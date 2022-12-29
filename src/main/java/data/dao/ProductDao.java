package data.dao;

import data.model.Product;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
@Named(value = "poOrderDao")
@RequestScoped
public class ProductDao {
    @PersistenceContext
    private EntityManager em;

    public void addProduct(Product product) {
        //TODO implement
    }

    public void deleteProduct() {
        //TODO implement
    }
    public List<Product> getAllProducts () {
        //TODO implement
        return Collections.emptyList();
    }
}
