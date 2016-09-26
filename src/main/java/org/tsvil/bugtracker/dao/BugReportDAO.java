package org.tsvil.bugtracker.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import org.tsvil.bugtracker.AppConfig;
import org.tsvil.bugtracker.entity.BugReport;
import org.tsvil.bugtracker.entity.BugReportBuilder;
import org.tsvil.bugtracker.entity.Label;
import org.tsvil.bugtracker.utils.EntityUtils;

public class BugReportDAO implements DBWriter {

    private Connection connection;
    private final DBConnect dbc;
    private final EntityUtils entityUtils;

    private final ProjectDAO projectDAO;
    private final LabelDAO labelDAO;

    public BugReportDAO() {
        dbc = new DBConnect();
        projectDAO = new ProjectDAO();
        labelDAO = new LabelDAO();
        entityUtils = new EntityUtils();
    }

    public ArrayList<BugReport> getAllBugReports() throws SQLException, IOException {
        String query = "select bug_report_id, date_reported, reporter, description, desired_resolution_date, priority,"
                + " state, date_resolved, date_updated, project, labels from `" + AppConfig.getDbName() + "`.bug_report;";
        Statement stmt = null;
        ArrayList<BugReport> allBugReports = new ArrayList<>();
        try {
            connection = dbc.getConnection();
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                allBugReports.add(extractBugReportFromRs(rs));
            }
        } catch (SQLException | NamingException | IOException ex) {
            Logger.getLogger(BugReportDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return allBugReports;
    }

    private BugReport extractBugReportFromRs(ResultSet rs) throws SQLException {
        BugReportBuilder builder = new BugReportBuilder();
        BugReport result = builder.bugReportId(rs.getLong("bug_report_id"))
                .dateReported(rs.getDate("date_reported"))
                .reporter(rs.getString("reporter"))
                .description(rs.getString("description"))
                .desiredResolutionDate(rs.getDate("desired_resolution_date"))
                .priority(entityUtils.resolvePriority(rs.getInt("priority")))
                .state(entityUtils.resolveState(rs.getInt("state")))
                .dateResolved(rs.getDate("date_resolved"))
                .dateUpdated(rs.getDate("date_updated"))
                .project(projectDAO.getProjectById(rs.getInt("project")))
                .labels(parseLabelsString(rs.getString("labels")))
                .build();
        return result;
    }

    private Label[] parseLabelsString(String labelsStr) {
        Label[] labels = null;
        if (labelsStr == null) {
            return labels;
        } 
        String[] ids = labelsStr.split(",");
        
        for (int i = 0; i < 0; i++) {
            labels[i] = labelDAO.getLabelById(Integer.parseInt(ids[i]));
        }
        return labels;
    }

    public void doTest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
