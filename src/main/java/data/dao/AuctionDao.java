/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import data.model.Auction;
import data.model.Bid;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 *
 * @author Gheoace Mircea
 */
@Named(value = "auctionDao")
@RequestScoped
public class AuctionDao {

    @PersistenceContext
    private EntityManager em;

    public Auction addAuction(Auction auction) {
        this.em.persist(auction);
        this.em.flush();
        return auction;
    }
    
    public boolean deleteAuction(int id){
        Integer deletedBids=this.em.createNamedQuery("Bid.deleteBidByAuction", Integer.class).setParameter("auctionId",id).executeUpdate();
        System.out.println("deletedBids : " + deletedBids);
        int auctionNr = this.em.createQuery("DELETE FROM Auction a WHERE a.id = :id").setParameter("id",id).executeUpdate();
        return auctionNr == 1;
    }
    
    public List<Auction> getAllAuctions(){
        return this.em.createNamedQuery("Auction.findAll", Auction.class).getResultList();
    }
    
    public Auction findAuctionById(int id){
        return this.em.find(Auction.class, id);
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

}
