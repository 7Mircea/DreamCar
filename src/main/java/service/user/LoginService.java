/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.user;

import data.dao.UserDao;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import data.model.Users;

/**
 *
 * @author Gheoace Mircea
 */
@Named(value = "login")
@SessionScoped
public class LoginService implements Serializable {

    private String username;
    private String password;
    private Users users;

    @Inject
    UserDao userDao;

    public String login() {

        // Get the user
        this.users = userDao.getUser(this.username, this.password);

        // Check if the result is null
        if (this.users != null) {

            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

            // If the user is not null, check the role
            if (this.users.getRole().equals("vendor")) {
                session.setAttribute("user", "vendor");
                return "/vendor/auctions.xhtml?faces-redirect=true";
            } else if (this.users.getRole().equals("admin")) {
                session.setAttribute("user", "admin");
                return "/admin/auctions.xhtml?faces-redirect=true";
            }
        }

        // Redirect again to login page.
        return "/public/login.xhtml?redirect=true";

    }

    public String logout() {

        FacesContext instance = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) instance.getExternalContext().getSession(false);
        session.invalidate();
        return "/index.xhtml?faces-redirect=true";
    }

    public void isAdmin(ComponentSystemEvent event) {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        Object isLogged = session.getAttribute("user");

        if (isLogged == null) {
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();
            nav.performNavigation("/public/login.xhtml?faces-redirect=true");

        } else if ("vendor".equals(isLogged)) {
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();
            nav.performNavigation("/public/login.xhtml?faces-redirect=true");
        }

    }

    public void isVendor(ComponentSystemEvent event) {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        Object isLogged = session.getAttribute("user");

        if (isLogged == null) {
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();
            nav.performNavigation("/public/login.xhtml?faces-redirect=true");

        } else if ("admin".equals(isLogged)) {
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();
            nav.performNavigation("/public/login.xhtml?faces-redirect=true");
        }
    }

    public void removeSessionAttributeAfterRender(PhaseEvent event) {
        if (event.getPhaseId() == PhaseId.RENDER_RESPONSE) {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
            session.removeAttribute("renderMessage");
            session.removeAttribute("message");
        }

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Users getUser() {
        return users;
    }

    public void setUser(Users users) {
        this.users = users;
    }

}
