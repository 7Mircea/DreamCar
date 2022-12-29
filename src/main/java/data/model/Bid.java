/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Gheoace Mircea
 */
@Entity
@Table(name = "BIDS")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Bid.findAll", query = "SELECT b FROM Bid b")
        , @NamedQuery(name = "Bid.findByBidId", query = "SELECT b FROM Bid b WHERE b.bidId = :bidId")
        , @NamedQuery(name = "Bid.findByBidDate", query = "SELECT b FROM Bid b WHERE b.bidDate = :bidDate")
        , @NamedQuery(name = "Bid.findByPrice", query = "SELECT b FROM Bid b WHERE b.price = :price")})
public class Bid implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Size(max = 30)
    @Column(name = "date")
    private String date;

    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private int price;

    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private Boolean status;

    @JoinColumn(name = "auction_id", referencedColumnName = "id")
    @ManyToOne(targetEntity = Auction.class, optional = false, fetch = FetchType.EAGER)
    private Auction auction;

    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User user;

    public Bid() {
    }

    public Bid(Integer id) {
        this.id = id;
    }

    public Bid(Integer id, int price) {
        this.id = id;
        this.price = price;
    }

    public Integer getBidId() {
        return id;
    }

    public void setBidId(Integer bidId) {
        this.id = id;
    }

    public String getBidDate() {
        return date;
    }

    public void setBidDate(String bidDate) {
        this.bidDate = bidDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bidId != null ? bidId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bid)) {
            return false;
        }
        Bid other = (Bid) object;
        if ((this.bidId == null && other.bidId != null) || (this.bidId != null && !this.bidId.equals(other.bidId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Bid[ bidId=" + bidId + " ]";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
