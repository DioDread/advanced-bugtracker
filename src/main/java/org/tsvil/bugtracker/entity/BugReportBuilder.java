package org.tsvil.bugtracker.entity;

import java.util.Date;

public class BugReportBuilder {

    private final BugReport bugReport;

    public BugReportBuilder() {
        bugReport = new BugReport();
    }

    public BugReportBuilder bugReportId(long id) {
        bugReport.setBugReportId(id);
        return this;
    }

    public BugReportBuilder name(String name) {
        bugReport.setName(name);
        return this;
    }

    public BugReportBuilder dateReported(Date date) {
        bugReport.setDateReported(date);
        return this;
    }

    public BugReportBuilder reporter(String name) {
        bugReport.setReporter(name);
        return this;
    }

    public BugReportBuilder description(String text) {
        bugReport.setDescription(text);
        return this;
    }

    public BugReportBuilder desiredResolutionDate(Date date) {
        bugReport.setDesiredResolutionDate(date);
        return this;
    }

    public BugReportBuilder priority(Priority priority) {
        bugReport.setPriority(priority);
        return this;
    }

    public BugReportBuilder state(State state) {
        bugReport.setState(state);
        return this;
    }

    public BugReportBuilder dateResolved(Date date) {
        bugReport.setDateResolved(date);
        return this;
    }

    public BugReportBuilder dateUpdated(Date date) {
        bugReport.setDateUpdated(date);
        return this;
    }

    public BugReportBuilder project(Project project) {
        bugReport.setProject(project);
        return this;
    }

    public BugReportBuilder labels(String labels) {
        bugReport.setLabels(labels);
        return this;
    }

    public BugReport build() {
        return bugReport;
    }
}
