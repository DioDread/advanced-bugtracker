package org.tsvil.bugtracker.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.tsvil.bugtracker.dao.UserDAO;
import org.tsvil.bugtracker.entity.Credentials;
import org.tsvil.bugtracker.entity.User;

public class UserServlet extends HttpServlet {
    
    private UserDAO userDAO;

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            if (req.getParameter("username") != null) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            authorize(new Credentials(username, password), req, resp);
        } else {
            doGet(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/authorize.jsp").forward(req, resp);
    }

    private void authorize(Credentials creds, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userDAO = new UserDAO();
        
        try {
            //TODO Add hashing logic for password security;
            User user = userDAO.findUserByUsername(creds.getUsername());
            if (user.getPassword() == null ? creds.getPassword() == null : user.getPassword().equals(creds.getPassword())) {
                req.getSession().setAttribute("username", user.getUsername());
                req.getSession().setAttribute("userId", user.getUserId());
                req.getSession().setAttribute("userRole", user.getRole());
                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.getWriter().write("{ \"success\": true, \"message\": \"\" }");
            } else {
                resp.setStatus(401);
                resp.getWriter().write("{ \"success\": false, \"message\": \"Authentication error\" }");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void register(Credentials creds) {

    }

    private void showAuthorizationForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/authorize.jsp").forward(req, resp);
    }

    private void showRegistrationForm() {

    }

}
