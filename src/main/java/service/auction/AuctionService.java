/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.auction;

import data.dao.AuctionDao;
import data.dao.CharacteristicDao;
import data.dao.ProductDao;
import data.model.Auction;
import data.model.Characteristic;
import data.model.Product;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.transaction.*;
import java.util.List;

/**
 * @author Gheoace Mircea
 */
@Named(value = "auction")
@RequestScoped
public class AuctionService {

    private UIComponent component;
    private Auction auction;

    private Product product;

    private int productId;


    private List<Auction> auctions;
    private List<Integer> productIds;

    @Inject
    private AuctionDao auctionDao;
    @Inject
    private ProductDao productDao;


    @Resource
    private UserTransaction utx;

    @PostConstruct
    public void init() {
        this.auction = new Auction();
        this.auctions = this.auctionDao.getAllAuctions();
        this.productIds = productDao.getAllProductIds();
    }

    public List<Integer> getProductIds() {
        return this.productIds;
    }

    public Product getProduct() {
        return product;
    }

//    public void setProduct(Product product) {
//        this.product = product;
//    }

    public void setProduct() {
        System.out.println("product id is : " + productId);
        if (productId > 0) {
            this.product = productDao.getProductById(productId);
            System.out.println("product is " + product);
        }
    }

    public void addAuction() {
        product = productDao.getProductById(productId);
        System.out.println("we are in addAuction()");
        System.out.println("utx is null" + (utx == null));
        System.out.println("auction is null " + (auction == null));
        if(auction != null) {
            System.out.println(auction);
        }
        System.out.println("product is null " + (product == null));
        System.out.println("productDao is null " + (productDao == null));
        System.out.println("auctionDao is null " + (auctionDao == null));
        try {
            utx.begin();
            this.auction.setStatus(Boolean.TRUE);
            this.auction.setProduct(productDao.getProductById(productId));
            this.productDao.addProduct(product);
            this.auctionDao.addAuction(this.auction);
            utx.commit();

            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("renderMessage", true);
            session.setAttribute("message", " The auction has been added!");

            FacesContext context = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();
            nav.performNavigation("/admin/auctions.xhtml?faces-redirect=true");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Something wrong happen with addAuction() transaction");
        }

    }

    public void closeAuction(int id) {

        Auction auction_db = this.auctionDao.findAuctionById(id);

        try {

            // Update the status
            utx.begin();
            auction_db.setStatus(Boolean.FALSE);
            this.auctionDao.getEm().merge(auction_db);
            this.auctionDao.getEm().flush();
            utx.commit();

            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("renderMessage", true);
            session.setAttribute("message", " The auction has been closed!");

            // Reload the home page
            FacesContext context = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();
            nav.performNavigation("/admin/auctions.xhtml?faces-redirect=true");

        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException |
                 NotSupportedException | RollbackException | SystemException e) {
            e.printStackTrace();
        }
    }

    public void openAuction(int id) {

        Auction auction_db = this.auctionDao.findAuctionById(id);

        try {

            // Update the status
            utx.begin();
            auction_db.setStatus(Boolean.TRUE);
            this.auctionDao.getEm().merge(auction_db);
            this.auctionDao.getEm().flush();
            utx.commit();

            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("renderMessage", true);
            session.setAttribute("message", " The auction has been opened!");

            // Reload the home page
            FacesContext context = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();
            nav.performNavigation("/admin/auctions.xhtml?faces-redirect=true");

        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException |
                 NotSupportedException | RollbackException | SystemException e) {
            e.printStackTrace();
        }
    }

    public void deleteAuction(int id) {

        try {

            utx.begin();
            this.auctionDao.deleteAuction(id);
            utx.commit();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException |
                 NotSupportedException | RollbackException | SystemException e) {
            e.printStackTrace();
        }

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.setAttribute("renderMessage", true);
        session.setAttribute("message", " The auction has been deleted!");

        // Reload the home page
        FacesContext context = FacesContext.getCurrentInstance();
        ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();
        nav.performNavigation("/admin/auctions.xhtml?faces-redirect=true");
    }

    /**
     * check if the opened auctions can be closed
     */
    public void checkAuctions() {

    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
