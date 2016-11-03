package org.tsvil.bugtracker.entity;

public enum Priority {
    UNSPECIFIED(0), MINOR(1), MEDIUM(2), MAJOR(3), CRITICAL(4), BLOCKER(5);

    private final int value;

    private Priority(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return this.value;
    }
}
