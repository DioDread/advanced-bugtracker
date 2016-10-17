package org.tsvil.bugtracker.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.naming.ConfigurationException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.tsvil.bugtracker.dao.BugReportDAO;
import org.tsvil.bugtracker.entity.State;

public class StatisticsServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BugReportDAO brDAO = new BugReportDAO();

        try {
            String statsJson = brDAO.getAllBugReports().stream()
//                    .filter(r -> r.getState() != State.CLOSED && r.getState() != State.FIXED)
                    .map(r -> Json.createObjectBuilder().add(r.getDateReported().toString(), r.getName()).build().toString())
                    .collect(Collectors.joining(",", "[", "]"));
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.getWriter().write(statsJson);
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConfigurationException ex) {
            Logger.getLogger(StatisticsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
