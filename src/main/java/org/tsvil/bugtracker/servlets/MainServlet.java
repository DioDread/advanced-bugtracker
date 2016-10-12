package org.tsvil.bugtracker.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.naming.ConfigurationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.tsvil.bugtracker.dao.BugReportDAO;
import org.tsvil.bugtracker.dao.ProjectDAO;
import org.tsvil.bugtracker.entity.BugReport;
import org.tsvil.bugtracker.entity.Project;
import org.tsvil.bugtracker.entity.State;
import org.tsvil.bugtracker.utils.EntityUtils;

public class MainServlet extends HttpServlet {

    private final EntityUtils entityUtils = new EntityUtils();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BugReportDAO bugReportDAO = new BugReportDAO();
        ArrayList<BugReport> reports = new ArrayList<>();
        boolean isJson = false;
        if (req.getParameter("response") != null) {
            isJson = req.getParameter("response").equals("json");
        }

        try {
            reports = bugReportDAO.getAllBugReports();
        } catch (SQLException ex) {
            Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConfigurationException ex) {
            Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (isJson) {
            String jsonResponse = reports.stream()
                    .map(r -> r.toJson()).map(j -> j.toString())
                    .collect(Collectors.joining(", ", "[", "]"));
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonResponse);
        } else {
            req.setAttribute("reports", reports);
            req.getRequestDispatcher("/main-view.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        BugReportDAO bugReportDAO = new BugReportDAO();
        ProjectDAO projectDAO = new ProjectDAO();

        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

        String name = req.getParameter("name");
        String reporter = req.getParameter("reporter");
        String description = req.getParameter("description");
        String labels = req.getParameter("labels-data");
        int priority = Integer.parseInt(req.getParameter("priority"));

        try {
            Project project = projectDAO.getProjectById(Integer.parseInt(req.getParameter("project")));
            Date desiredResolutionDate = sdf.parse(req.getParameter("desiredResolutionDate"));

            BugReport newReport = new BugReport(
                    bugReportDAO.getLastId(),
                    name,
                    new Date(),
                    reporter,
                    entityUtils.resolvePriority(priority),
                    State.REPORTED,
                    project
            );
            newReport.setDesiredResolutionDate(desiredResolutionDate);
            newReport.setDescription(description);
            newReport.setLabels(labels);
            bugReportDAO.insertBugReport(newReport);
        } catch (SQLException ex) {
            Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConfigurationException ex) {
            Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
