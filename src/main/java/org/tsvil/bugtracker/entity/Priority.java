/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tsvil.bugtracker.entity;

/**
 *
 * @author Ales_Tsvil
 */
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
