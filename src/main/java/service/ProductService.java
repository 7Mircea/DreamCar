package service;

import data.dao.ProductDao;
import data.model.Product;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named(value = "product")
@SessionScoped
public class ProductService implements Serializable {
    private UIComponent component;

    private List<Product> products;
    @Inject
    private ProductDao productDao;

    @PostConstruct
    public void init() {
        System.out.println("Product service is working");
        this.products = productDao.getAllProducts();
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }
}
