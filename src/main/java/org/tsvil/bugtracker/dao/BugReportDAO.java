package org.tsvil.bugtracker.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.ConfigurationException;
import javax.naming.NamingException;
import org.tsvil.bugtracker.AppConfig;
import org.tsvil.bugtracker.entity.BugReport;
import org.tsvil.bugtracker.entity.BugReportBuilder;
import org.tsvil.bugtracker.entity.Label;
import org.tsvil.bugtracker.entity.State;
import org.tsvil.bugtracker.utils.EntityUtils;
import org.tsvil.bugtracker.utils.PageInfo;

public class BugReportDAO implements DBWriter {

    private Connection connection;
    private Statement statement;
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

    public ArrayList<BugReport> getAllBugReports() throws SQLException, ConfigurationException {
        return selectBugReports(null, null, null, true);
    }

    public BugReport findBugReportById(int id) throws SQLException, ConfigurationException {
        return selectBugReports(id, null, null, false).get(0);
    }

    public ArrayList<BugReport> getBugReportsWithOffset(PageInfo pageInfo) throws SQLException, ConfigurationException {
        return selectBugReports(null, pageInfo, null, true);
    }

    public ArrayList<BugReport> getBugReportsWithFilterAndOffset(PageInfo pageInfo, State filter) throws SQLException, ConfigurationException {
        return selectBugReports(null, pageInfo, filter, true);
    }

    public void insertBugReport(BugReport br) throws SQLException, ConfigurationException {
        try {
            SimpleDateFormat sdtf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdt = new java.text.SimpleDateFormat("yyyy-MM-dd");
            String desiredResolutionDate = br.getDesiredResolutionDate() != null ? "'" + sdt.format(br.getDesiredResolutionDate()) + "'" : null;
            String dateResolved = br.getDateResolved() != null ? "'" + sdtf.format(br.getDateResolved()) + "'" : null;
            String dateUpdated = br.getDateUpdated() != null ? "'" + sdtf.format(br.getDateUpdated()) + "'" : null;
            String query = "insert into `" + AppConfig.getDbName() + "`.bug_report values(" + br.getBugReportId() + ", '"
                    + br.getName() + "', '" + sdtf.format(br.getDateReported()) + "', '" + br.getReporter() + "', '" + br.getDescription() + "', "
                    + desiredResolutionDate + ", "
                    + br.getPriority().getValue() + ", " + br.getState().getValue() + ", "
                    + dateResolved + ", "
                    + dateUpdated + ", "
                    + br.getProject().getProjectId() + ", '"
                    + br.getLabels() + "');";
            connection = dbc.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (IOException ex) {
            Logger.getLogger(BugReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConfigurationException("Application configuration failed, please contact your administrator");
        } finally {
            releaseResources();
        }
    }

    public void updateBugReport(BugReport br) throws SQLException, ConfigurationException {
        try {
            String query = "update `" + AppConfig.getDbName() + "`bug_report set bug_report_id=" + br.getBugReportId()
                    + ", name=" + br.getName() + ", date_reported=" + br.getDateReported() + ", reporter="
                    + br.getReporter() + ", description=" + br.getDescription() + ", desired_resolution_date="
                    + br.getDesiredResolutionDate() + ", priority=" + br.getPriority().getValue() + ", state="
                    + br.getState().getValue() + ", date_resolved=" + br.getDateResolved() + ", date_updated="
                    + br.getDateUpdated() + ", project=" + br.getProject().getProjectId() + ", labels="
                    + br.getLabels() + ";";
            connection = dbc.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (IOException ex) {
            Logger.getLogger(BugReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConfigurationException("Application configuration failed, please contact your administrator");
        } finally {
            releaseResources();
        }
    }

    public void deleteBugReport(int id) throws SQLException, ConfigurationException {
        try {
            String query = "delete from `" + AppConfig.getDbName() + "`.bug_report where bug_report_id=" + id + ";";
            connection = dbc.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (IOException ex) {
            Logger.getLogger(BugReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConfigurationException("Application configuration failed, please contact your administrator");
        } finally {
            releaseResources();
        }
    }

    private ArrayList<BugReport> selectBugReports(Integer id, PageInfo pageInfo, State filter, boolean isShortRecord) throws SQLException, ConfigurationException {
        String condition = ";";
        if (id != null) {
            condition = " where bug_report_id=" + id;
        } else if (filter != null) {
            condition = " where state=" + filter.getValue();
        } else if (pageInfo != null) {
            condition += " limit " + pageInfo.limit + " offset " + pageInfo.offset;
        }
        condition += ";";

        String fieldsToSelect;
        if (isShortRecord) {
            fieldsToSelect = "bug_report_id, name, date_reported, reporter, priority, state";
        } else {
            fieldsToSelect = "bug_report_id, name, date_reported, reporter, description, desired_resolution_date, "
                    + "priority, state, date_resolved, date_updated, project, labels";
        }
        ArrayList<BugReport> allBugReports = new ArrayList<>();
        try {

            String query = "select " + fieldsToSelect + " from `" + AppConfig.getDbName() + "`.bug_report" + condition;
            connection = dbc.getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                allBugReports.add(isShortRecord ? extractShortBugReporFromRs(rs) : extractBugReportFromRs(rs));
            }
        } catch (NamingException | IOException ex) {
            
        } finally {
            releaseResources();
        }

        return allBugReports;
    }

    public int getLastId() throws SQLException, ConfigurationException {
        int result = -1;
        try {
            String query = "select count(bug_report_id) as id_count from `" + AppConfig.getDbName() + "`.bug_report;";
            connection = dbc.getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                result = rs.getInt("id_count");
            }
        } catch (IOException ex) {
            Logger.getLogger(BugReportDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConfigurationException("Application configuration failed, please contact your administrator");
        } finally {
            releaseResources();
        }
        return result;
    }

    private BugReport extractShortBugReporFromRs(ResultSet rs) throws SQLException {
        BugReportBuilder builder = new BugReportBuilder();
        BugReport result = builder
                .bugReportId(rs.getLong("bug_report_id"))
                .name(rs.getString("name"))
                .dateReported(rs.getDate("date_reported"))
                .reporter(rs.getString("reporter"))
                .priority(entityUtils.resolvePriority(rs.getInt("priority")))
                .state(entityUtils.resolveState(rs.getInt("state")))
                .build();
        return result;
    }

    private BugReport extractBugReportFromRs(ResultSet rs) throws SQLException {
        BugReportBuilder builder = new BugReportBuilder();
        BugReport result = builder
                .bugReportId(rs.getLong("bug_report_id"))
                .name(rs.getString("name"))
                .dateReported(rs.getDate("date_reported"))
                .reporter(rs.getString("reporter"))
                .description(rs.getString("description"))
                .desiredResolutionDate(rs.getDate("desired_resolution_date"))
                .priority(entityUtils.resolvePriority(rs.getInt("priority")))
                .state(entityUtils.resolveState(rs.getInt("state")))
                .dateResolved(rs.getDate("date_resolved"))
                .dateUpdated(rs.getDate("date_updated"))
                .project(projectDAO.getProjectById(rs.getInt("project")))
                .labels(rs.getString("labels"))
                .build();
        return result;
    }

    private void releaseResources() throws SQLException {
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

}
