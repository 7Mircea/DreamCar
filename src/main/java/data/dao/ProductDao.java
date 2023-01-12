package data.dao;

import data.model.Product;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Named(value = "productDao")
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

    public List<Product> getAllProducts() {
        return this.em.createNamedQuery("Product.findAll", Product.class).getResultList();
    }

    public Product getProductById(int productId) {
        //TODO here it might return null although there is a product by this id
        return this.em.createNamedQuery("Product.findById", Product.class).setParameter("productId", productId).getSingleResult();
    }
}
