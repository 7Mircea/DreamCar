/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import data.model.Bid;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Gheoace Mircea
 */
@Named(value = "bidDao")
@RequestScoped
public class BidDao {

    @PersistenceContext
    private EntityManager em;

    public Bid addBid(Bid b) {
        this.em.persist(b);
        this.em.flush();
        return b;
    }

    public boolean deleteBid(int id) {
        Query query = this.em.createQuery("DELETE FROM Bid b WHERE b.id = :id");
        query.setParameter("id", id);
        int result = query.executeUpdate();
        return result > 0;
    }

    public Bid findBidById(int id) {
        return this.em.find(Bid.class, id);
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

}
