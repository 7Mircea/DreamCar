/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Gheoace Mircea
 */
@Entity
@Table(name = "AUCTIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Auction.findAll", query = "SELECT a FROM Auction a")
    , @NamedQuery(name = "Auction.findById", query = "SELECT a FROM Auction a WHERE a.id = :id")
    , @NamedQuery(name = "Auction.findByStatus", query = "SELECT a FROM Auction a WHERE a.status = :status")
    , @NamedQuery(name = "Auction.findByPostDate", query = "SELECT a FROM Auction a WHERE a.post_date = :post_date")
    , @NamedQuery(name = "Auction.findByProductId", query = "SELECT a FROM Auction a WHERE a.product_id = :product_id")
    , @NamedQuery(name = "Auction.findByDueDate", query = "SELECT a FROM Auction a WHERE a.dueDate = :dueDate")
    , @NamedQuery(name = "Auction.findByQuantity", query = "SELECT a FROM Auction a WHERE a.quantity = :quantity")})
public class Auction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "status",nullable = false)
    private Boolean status;
    @Size(max = 30)
    @Column(name = "date")
    private String date;

    @Size(max = 30)
    @Column(name = "due_date")
    private String dueDate;

    @Size(max = 50)
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "quantity")
    private Integer quantity;//quantity
    @OneToMany(targetEntity = Bid.class,cascade = CascadeType.ALL, mappedBy = "id", fetch = FetchType.EAGER)
    private List<Bid> bidList;

    public Auction() {
    }

    public Auction(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer AuctionId) {
        this.id = AuctionId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String AuctionDate) {
        this.date = AuctionDate;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer component) {
        this.productId = component;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @XmlTransient
    public List<Bid> getBidList() {
        return bidList;
    }

    public void setBidList(List<Bid> bidList) {
        this.bidList = bidList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Auction)) {
            return false;
        }
        Auction other = (Auction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Auction[ AuctionId=" + id + " ]";
    }

}
