/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Gheoace Mircea
 */
@Entity
@Table(name = "AUCTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Auction.findAll", query = "SELECT a FROM Auction a")
    , @NamedQuery(name = "Auction.findById", query = "SELECT a FROM Auction a WHERE a.id = :id")
    , @NamedQuery(name = "Auction.findByStatus", query = "SELECT a FROM Auction a WHERE a.status = :status")
    , @NamedQuery(name = "Auction.findByPostDate", query = "SELECT a FROM Auction a WHERE a.postDate = :post_date")
    , @NamedQuery(name = "Auction.findByProductId", query = "SELECT a FROM Auction a WHERE a.product = :product")
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
    @Column(name = "post_date")
    private String postDate;

    @Size(max = 30)
    @Column(name = "due_date")
    private String dueDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "quantity")
    private Integer quantity;//quantity
    @OneToMany(targetEntity = Bid.class,cascade = CascadeType.ALL, mappedBy = "auction", fetch = FetchType.EAGER)
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

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

//    @Override
//    public String toString() {
//        return "model.Auction[ AuctionId=" + id + " ]";
//    }


    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", status=" + status +
                ", postDate='" + postDate + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", product=" + product +
                ", quantity=" + quantity +
                ", bidList=" + bidList +
                '}';
    }
}
