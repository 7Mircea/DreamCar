package service.product;

import data.dao.CharacteristicDao;
import data.dao.ProductDao;
import data.model.Characteristic;
import data.model.Product;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.transaction.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Gheoace Mircea
 */
@Named(value = "productService")
@RequestScoped
public class ProductService {

    Product product;

    List<Characteristic> characteristics;

    @Inject
    ProductDao productDao;
    @Inject
    CharacteristicDao characteristicDao;
    @Resource
    private UserTransaction utx;

    @PostConstruct
    public void init() {
        this.product = new Product();
        this.characteristics = new LinkedList<>();
        this.characteristics.add(new Characteristic());
        this.characteristics.add(new Characteristic());
        AtomicInteger nr= new AtomicInteger();
        characteristics.forEach(characteristic -> characteristic.setCharacteristicId(nr.getAndIncrement()));
    }

    public void addProduct() {
        FacesContext fc = FacesContext.getCurrentInstance();
        try {
            this.utx.begin();
            this.productDao.addProduct(product);

            this.utx.commit();
//            this.utx.begin();
//            Product productSaved = this.productDao.getLastProduct();
//            System.out.println("product add id is " + productSaved.getId());
//            characteristics.stream().parallel().forEach(characteristic -> characteristic.setProductId(productSaved.getId()));
//            characteristicDao.addAll(characteristics);
//            this.utx.commit();


            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("renderMessage", true);
            session.setAttribute("message", " Your product has been added!");

            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("/admin/create-auction.xhtml?faces-redirect=true");


        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException |
                 NotSupportedException | RollbackException | SystemException e) {
            e.printStackTrace();

            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("/admin/auctions.xhtml?faces-redirect=true");
        }
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }
}
