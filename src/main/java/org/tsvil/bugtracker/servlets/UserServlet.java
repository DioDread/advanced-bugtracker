package org.tsvil.bugtracker.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.tsvil.bugtracker.entity.Credentials;

public class UserServlet extends HttpServlet {
    
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/authorize.jsp").forward(req, resp);
    }

    private void authorize(Credentials creds) {
        
    }
    
    private void register(Credentials creds) {
        
    }
    
    private void showAuthorizationForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/authorize.jsp").forward(req, resp);
    }
    
    private void showRegistrationForm() {
        
    }
    
}
