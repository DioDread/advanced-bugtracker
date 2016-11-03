package org.tsvil.bugtracker.entity;

public enum State {
    REPORTED(0), CONFIRMED(1), OPEN(2), NOT_A_BUG(3), FIXED(4), CLOSED(5), PENDING(6);

    private final int value;

    private State(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
