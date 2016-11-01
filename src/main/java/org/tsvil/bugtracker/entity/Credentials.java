package org.tsvil.bugtracker.entity;

public class Credentials {
    
    private final String username;
    private final String password;
    
    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    
}
