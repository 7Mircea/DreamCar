/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.*;

import data.model.Users;

/**
 *
 * @author Gheoace Mircea
 */
@Named(value = "userDao")
@RequestScoped
public class UserDao {

    @PersistenceContext
    private EntityManager em;

    public Users addUser(Users u) {
        this.em.persist(u);
        this.em.flush();
        return u;
    }

    public Users getUser(String username, String password) {
        try {
            return this.em.createNamedQuery("User.findByUsernameAndPassword", Users.class).setParameter("user", username).setParameter("pass", password).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<Users> getAllVendors(String role){
        return this.em.createNamedQuery("User.findByRole", Users.class).setParameter("role", role).getResultList();
    }
    
    public Users getUserById(int id){
        return this.em.createNamedQuery("User.findById", Users.class).setParameter("id",id).getSingleResult();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

}
