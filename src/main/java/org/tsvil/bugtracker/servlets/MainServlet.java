package org.tsvil.bugtracker.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.tsvil.bugtracker.dao.BugReportDAO;

public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BugReportDAO bugReportedDAO = new BugReportDAO();
        
        try {
            bugReportedDAO.doTest();
        } catch (SQLException ex) {
            Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        req.setAttribute("message", "text");
        req.getRequestDispatcher("/main-view.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    }
}
