/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.user;

import data.dao.CompanyDao;
import data.dao.UserDao;
import data.model.Company;
import data.model.User;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.*;
import java.util.List;

/**
 *
 * @author Gheoace Mircea
 */
@Named(value = "user")
@RequestScoped
public class UserService {

    private User user;
    private Company company;
    private List<User> vendors;

    @Inject
    private UserDao userDao;

    @Inject
    private CompanyDao companyDao;

    @Inject
    private LoginService authServ;

    @Resource
    UserTransaction utx;

    @PostConstruct
    public void init() {
        this.user = new User();
        this.company = new Company();
        this.vendors = this.userDao.getAllVendors("vendor");
    }

    public void register() {

        // persist the user into database
        try {
            this.utx.begin();
            // First, add the user
            this.user.setRole("vendor");
            User user = this.userDao.addUser(this.user);

            // Second, add the company
            Company comp = this.companyDao.addCompany(this.company);

            // Third, add the user profile 
            this.user.setCompany(comp);
            this.user.setId(user.getId());
//            this.userDao.addProfile(this.profile);

            this.utx.commit();
            this.userDao.getEm().getEntityManagerFactory().getCache().evictAll();

            // set the message for success
            String message = "The user has been registered!";

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(message));
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            System.out.println("Register user failed to persist");
            throw new RuntimeException();
        }

    }

    public void updateUserProfile() {

        User updated_user = this.authServ.getUser();
        Company updated_user_company = updated_user.getCompany();

        try {
            this.utx.begin();
            this.userDao.getEm().merge(updated_user);
            this.userDao.getEm().flush();
            this.userDao.getEm().merge(updated_user);
            this.userDao.getEm().flush();
            this.companyDao.getEm().merge(updated_user_company);
            this.companyDao.getEm().flush();
            this.utx.commit();

            // Reload the profile page
            FacesContext context = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();
            nav.performNavigation("/vendor/user.xhtml?faces-redirect=true");

        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            e.printStackTrace();
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<User> getVendors() {
        return vendors;
    }

    public void setVendors(List<User> vendors) {
        this.vendors = vendors;
    }

}
