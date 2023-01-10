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

        // Set the query
        String query = "SELECT u from Users u where u.username = :user and u.password = :pass";

        // Interogate the database
        try {
            Query q = this.em.createQuery(query);

            q.setParameter("user", username);
            q.setParameter("pass", password);

            // Return the user
            return (Users) q.getSingleResult();

        } catch (NoResultException e) {

            // If no result, then return null
            return null;
        }
    }
    
    public List<Users> getAllVendors(String role){
        List<Users> query = this.em.createNamedQuery("User.findByRole", Users.class).setParameter("role", role).getResultList();
        return query;
    }
    
    public Users getUserById(int id){
        Query query = this.em.createNamedQuery("User.findById");
        query.setParameter("id", id);
        Users users = (Users) query.getSingleResult();
        return users;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

}
