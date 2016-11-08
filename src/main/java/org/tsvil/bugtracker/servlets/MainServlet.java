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
import org.tsvil.bugtracker.dao.UserDAO;
import org.tsvil.bugtracker.entity.BugReport;
import org.tsvil.bugtracker.entity.Project;
import org.tsvil.bugtracker.entity.Role;
import org.tsvil.bugtracker.entity.State;
import org.tsvil.bugtracker.entity.User;
import org.tsvil.bugtracker.utils.EntityUtils;

public class MainServlet extends HttpServlet {

    private final EntityUtils entityUtils = new EntityUtils();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        BugReportDAO bugReportDAO = new BugReportDAO();
        ArrayList<BugReport> reports = new ArrayList<>();
        Role role = (Role) req.getSession().getAttribute("userRole");
        boolean isJson = false;
        if (req.getParameter("response") != null) {
            isJson = req.getParameter("response").equals("json");
        }

        try {
            if (role.getRoleId() == 1) {
                Long userId = (Long) req.getSession().getAttribute("userId");
                reports = bugReportDAO.getBugReportsForSpecificUser(userId);
            } else {
                reports = bugReportDAO.getAllBugReports();
            }
        } catch (SQLException ex) {
            handleSQLException(resp);
            Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConfigurationException ex) {
            Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (isJson && reports != null) {
            String jsonResponse = reports.stream()
                    .map(r -> r.toJson()).map(j -> j.toString())
                    .collect(Collectors.joining(", ", "[", "]"));
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonResponse);
        } else {
            req.setAttribute("username", req.getSession().getAttribute("username"));
            req.setAttribute("reports", reports);
            req.setAttribute("role", role);
            req.getRequestDispatcher("/main-view.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        BugReportDAO bugReportDAO = new BugReportDAO();
        ProjectDAO projectDAO = new ProjectDAO();
        UserDAO userDAO = new UserDAO();

        ArrayList<User> allDevs = userDAO.getAllDevelopers();

        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

        String name = req.getParameter("name");
        String reporter = req.getParameter("reporter");
        String description = req.getParameter("description");
        String labels = req.getParameter("labels-data");
        int priority = Integer.parseInt(req.getParameter("priority"));

        try {
            Project project = projectDAO.getProjectById(Integer.parseInt(req.getParameter("project")));
            Date desiredResolutionDate = sdf.parse(req.getParameter("desiredResolutionDate"));
            User choosen = getRandomUser(allDevs);
            User reporterUser = new User();
            reporterUser.setUserId((long) req.getSession().getAttribute("userId"));
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
            newReport.setAssignedUser(choosen);
            newReport.setReportedUser(reporterUser);
            bugReportDAO.insertBugReport(newReport);
        } catch (SQLException ex) {
            handleSQLException(resp);
            Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConfigurationException ex) {
            Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BugReportDAO bugReportDAO = new BugReportDAO();

        int bugId = Integer.parseInt(req.getParameter("bug-report-id"));

        try {
            bugReportDAO.deleteBugReport(bugId);
        } catch (SQLException ex) {
            Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
            handleSQLException(resp);
        } catch (ConfigurationException ex) {
            Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleSQLException(HttpServletResponse resp) throws IOException {
        resp.setStatus(500);
        resp.setContentType("text/html");
        resp.getWriter().write("Error during DB access.");
    }

    private User getRandomUser(ArrayList<User> users) {
        int maxUser = users.size() - 1;
        int randomIndex = Math.round((float) (Math.random() * maxUser));

        return users.get(randomIndex);
    }
}
