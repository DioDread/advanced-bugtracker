package org.tsvil.bugtracker.entity;

import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;

public class BugReport {

    private long bugReportId;
    private String name;
    private Date dateReported;
    private String reporter;
    private String description;
    private Date desiredResolutionDate;
    private Priority priority;
    private State state;
    private Date dateResolved;
    private Date dateUpdated;
    private Project project;
    private String labels;
    private User assignedUser;
    private User reportedUser;

    public BugReport() {
    }

    public BugReport(long id, String name, Date reported, String reporter, Priority priority, State state, Project project) {
        this.bugReportId = id;
        this.name = name;
        this.dateReported = reported;
        this.reporter = reporter;
        this.priority = priority;
        this.state = state;
        this.project = project;
    }

    /**
     * @return the bugReportId
     */
    public long getBugReportId() {
        return bugReportId;
    }

    /**
     * @param bugReportId the bugReportId to set
     */
    public void setBugReportId(long bugReportId) {
        this.bugReportId = bugReportId;
    }

    /**
     * @return the dateReported
     */
    public Date getDateReported() {
        return dateReported;
    }

    /**
     * @param dateReported the dateReported to set
     */
    public void setDateReported(Date dateReported) {
        this.dateReported = dateReported;
    }

    /**
     * @return the reporter
     */
    public String getReporter() {
        return reporter;
    }

    /**
     * @param reporter the reporter to set
     */
    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the desiredResolutionDate
     */
    public Date getDesiredResolutionDate() {
        return desiredResolutionDate;
    }

    /**
     * @param desiredResolutionDate the desiredResolutionDate to set
     */
    public void setDesiredResolutionDate(Date desiredResolutionDate) {
        this.desiredResolutionDate = desiredResolutionDate;
    }

    /**
     * @return the priority
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * @return the state
     */
    public State getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * @return the dateResolved
     */
    public Date getDateResolved() {
        return dateResolved;
    }

    /**
     * @param dateResolved the dateResolved to set
     */
    public void setDateResolved(Date dateResolved) {
        this.dateResolved = dateResolved;
    }

    /**
     * @return the dateUpdated
     */
    public Date getDateUpdated() {
        return dateUpdated;
    }

    /**
     * @param dateUpdated the dateUpdated to set
     */
    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    /**
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * @param project the project to set
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * @return the labels
     */
    public String getLabels() {
        return labels;
    }

    /**
     * @param labels the labels to set
     */
    public void setLabels(String labels) {
        this.labels = labels;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the assignedUser
     */
    public User getAssignedUser() {
        return assignedUser;
    }

    /**
     * @param assignedUser the assignedUser to set
     */
    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    /**
     * @return the reportedUser
     */
    public User getReportedUser() {
        return reportedUser;
    }

    /**
     * @param reportedUser the reportedUser to set
     */
    public void setReportedUser(User reportedUser) {
        this.reportedUser = reportedUser;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("bugReportId", bugReportId)
                .add("name", name)
                .add("reporter", reporter)
                .add("description", description != null ? description : "")
                .add("desiredResolutionDate", desiredResolutionDate != null ? desiredResolutionDate.toString() : "")
                .add("dateReported", dateReported.toString())
                .add("priority", priority.getValue())
                .add("state", state.getValue())
                .add("dateResolved", dateResolved != null ? dateResolved.toString() : "")
                .add("dateUpdated", dateUpdated != null ? dateUpdated.toString() : "")
                .add("labels", labels != null ? labels : "")
                .add("project", project != null ? project.getName() : "")
                .build();
    }

}
