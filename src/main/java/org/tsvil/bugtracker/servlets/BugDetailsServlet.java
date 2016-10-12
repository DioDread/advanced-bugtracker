package org.tsvil.bugtracker.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.naming.ConfigurationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.tsvil.bugtracker.dao.BugReportDAO;
import org.tsvil.bugtracker.dao.ProjectDAO;
import org.tsvil.bugtracker.entity.BugReport;
import org.tsvil.bugtracker.entity.Project;
import org.tsvil.bugtracker.utils.EntityUtils;

public class BugDetailsServlet extends HttpServlet {

    private final EntityUtils entityUtils = new EntityUtils();

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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        BugReportDAO bugReportDAO = new BugReportDAO();
        ProjectDAO projectDAO = new ProjectDAO();

        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

        int id = Integer.parseInt(req.getParameter("bug-report-id"));
        String name = req.getParameter("name");
        String reporter = req.getParameter("reporter");
        String description = req.getParameter("description");
        String labels = req.getParameter("labels-data");
        int projectId = Integer.parseInt(req.getParameter("project"));
        int state = Integer.parseInt(req.getParameter("state"));
        int priority = Integer.parseInt(req.getParameter("priority"));

        try {
            Project project = projectDAO.getProjectById(projectId);
            Date desiredResolutionDate = sdf.parse(req.getParameter("desiredResolutionDate"));

            BugReport existingReport = bugReportDAO.findBugReportById(id);
            
            existingReport.setName(name);
            existingReport.setReporter(reporter);
            existingReport.setDateUpdated(new Date());
            existingReport.setDesiredResolutionDate(desiredResolutionDate);
            existingReport.setDescription(description);
            existingReport.setState(entityUtils.resolveState(state));
            existingReport.setPriority(entityUtils.resolvePriority(priority));
            existingReport.setLabels(labels);
            existingReport.setProject(project);
            
            bugReportDAO.updateBugReport(existingReport);
        } catch (SQLException ex) {
            Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConfigurationException ex) {
            Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
