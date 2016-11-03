package org.tsvil.bugtracker.entity;

public class Assignment {

    private long assignmentId;
    private User user;
    private BugReport bugReport;

    public Assignment(long assignmentId, User user, BugReport bugReport) {
        this.assignmentId = assignmentId;
        this.user = user;
        this.bugReport = bugReport;
    }

    /**
     * @return the assignmentId
     */
    public long getAssignmentId() {
        return assignmentId;
    }

    /**
     * @param assignmentId the assignmentId to set
     */
    public void setAssignmentId(long assignmentId) {
        this.assignmentId = assignmentId;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the bugReport
     */
    public BugReport getBugReport() {
        return bugReport;
    }

    /**
     * @param bugReport the bugReport to set
     */
    public void setBugReport(BugReport bugReport) {
        this.bugReport = bugReport;
    }
}
