package org.tsvil.bugtracker.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.ConfigurationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.tsvil.bugtracker.dao.BugReportDAO;
import org.tsvil.bugtracker.entity.BugReport;

public class BugDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BugReportDAO reportDAO = new BugReportDAO();
        int bugReportId = Integer.parseInt(req.getParameter("id"));
        try {
            BugReport result = reportDAO.findBugReportById(bugReportId);
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.getWriter().write(result.toJson().toString());
        } catch (SQLException ex) {
            Logger.getLogger(BugDetailsServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConfigurationException ex) {
            Logger.getLogger(BugDetailsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

    }
}
