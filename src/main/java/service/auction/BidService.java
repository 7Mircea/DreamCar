/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.auction;

import data.dao.BidDao;
import data.dao.AuctionDao;
import data.dao.UserDao;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import data.model.Bid;
import data.model.Auction;
import data.model.User;
import service.user.LoginService;

/**
 *
 * @author Gheoace Mircea
 */
@Named(value = "bidService")
@RequestScoped
public class BidService {

    private int auctionId;
    private UIComponent component;
    private Bid bid;
    private Auction auction;
    private List<Bid> bids;
    private List<Bid> userBids;
    private User user;

    @Inject
    private UserDao userDao;

    @Inject
    private BidDao bidDao;

    @Inject
    private AuctionDao auctionDao;

    @Inject
    private LoginService auth;

    @Resource
    private UserTransaction utx;

    @PostConstruct
    public void init() {
        this.bid = new Bid();
        User dbUser = this.userDao.getUserById(this.auth.getUser().getId());
        this.userBids = dbUser.getBidList();
    }

    public void addBid() {

        // Get the param send from create-bid.xhtml page
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        // Get the auction from database
        this.auction = this.auctionDao.findAuctionById(Integer.parseInt(params.get("auctionId")));

        // Get the current date
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String offerDate = formatter.format(date);

        try {

            if (this.auction != null) {
                // Create the offer
                this.utx.begin();
                this.bid.setUser(auth.getUser());
//                this.bid.setPoNumber(auction);
                this.bid.setBidDate(offerDate);
                this.bid.setStatus(Boolean.FALSE);
                this.bidDao.addBid(this.bid);
                this.utx.commit();

                this.bidDao.getEm().getEntityManagerFactory().getCache().evictAll();

                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                session.setAttribute("renderMessage", true);
                session.setAttribute("message", " Your offer has been added to the auction!");

                ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
                nav.performNavigation("/vendor/auctions.xhtml?faces-redirect=true");

            }

        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            e.printStackTrace();

            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("/vendor/auctions.xhtml?faces-redirect=true");
        }
    }

    public void getAllBidsForAnAuction() {

        // get the Auction
        Auction selected_auction = this.auctionDao.findAuctionById(this.auctionId);

        if (selected_auction != null) {

            this.bids = selected_auction.getBidList();
            this.auction = selected_auction;
        }
    }

    public String acceptBid() {

        // Get the param send from create-bid.xhtml page
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.auctionId = Integer.parseInt(params.get("auctionId"));
        this.auction = this.auctionDao.findAuctionById(this.auctionId);
        String bidId = params.get("bidId");

        if (bidId != null) {
            this.bid = this.bidDao.findBidById(Integer.parseInt(bidId));
            try {
                this.utx.begin();
                this.bid.setStatus(Boolean.TRUE);
                this.bidDao.getEm().merge(this.bid);
                this.bidDao.getEm().flush();
                this.utx.commit();

            } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
                e.printStackTrace();
            }
        }

        return "/admin/bids.xhtml?faces-redirect=true&auctionId=" + this.auctionId;

    }

    public String declineBid() {

        // Get the param send from create-bid.xhtml page
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        this.auctionId = Integer.parseInt(params.get("auctionId"));
        this.auction = this.auctionDao.findAuctionById(this.auctionId);
        String bidId = params.get("bidId");

        if (bidId != null) {
            this.bid = this.bidDao.findBidById(Integer.parseInt(bidId));
            try {
                this.utx.begin();
                this.bid.setStatus(Boolean.FALSE);
                this.bidDao.getEm().merge(this.bid);
                this.bidDao.getEm().flush();
                this.utx.commit();

            } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
                e.printStackTrace();
            }
        }

        return "/admin/bids.xhtml?faces-redirect=true&auctionId=" + this.auctionId;

    }

    public String deleteBid(int id) {

        try {

            this.utx.begin();
            this.bidDao.deleteBid(id);
            this.utx.commit();

            User dbUser = this.userDao.getUserById(this.auth.getUser().getId());
            this.userBids = dbUser.getBidList();
            this.bidDao.getEm().getEntityManagerFactory().getCache().evictAll();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "/vendor/user.xhtml?faces-redirect=true";
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public List<Bid> getUserBids() {
        return userBids;
    }

    public void setUserBids(List<Bid> userBids) {
        this.userBids = userBids;
    }

}
