/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import data.model.Auction;

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
@Named(value = "AuctionDao")
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
        
        Query query = this.em.createQuery("DELETE FROM Auction a WHERE a.id = :id");
        query.setParameter("id", id);
        int auctionNr = query.executeUpdate();
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
