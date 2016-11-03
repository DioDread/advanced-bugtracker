package org.tsvil.bugtracker.entity;

public class Project {
    private long projectId;
    private String name;
    
    public Project(long projectId, String name) {
        this.projectId = projectId;
        this.name = name;
    }

    /**
     * @return the projectId
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * @param projectId the projectId to set
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
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
}
