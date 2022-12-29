package data.dao;

import data.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

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
