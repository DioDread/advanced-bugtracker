package org.tsvil.bugtracker.utils;

import org.tsvil.bugtracker.entity.Priority;
import org.tsvil.bugtracker.entity.State;

public class EntityUtils {

    public State resolveState(int stateId) {
        switch (stateId) {
            case 0:
                return State.REPORTED;
            case 1:
                return State.CONFIRMED;
            case 2:
                return State.OPEN;
            case 3:
                return State.NOT_A_BUG;
            case 4:
                return State.FIXED;
            case 5:
                return State.CLOSED;
            case 6:
                return State.PENDING;
            default:
                return State.OPEN;
        }
    }

    public Priority resolvePriority(int priorityId) {
        switch (priorityId) {
            case 0:
                return Priority.UNSPECIFIED;
            case 1:
                return Priority.MINOR;
            case 2:
                return Priority.MEDIUM;
            case 3:
                return Priority.MAJOR;
            case 4:
                return Priority.CRITICAL;
            case 5:
                return Priority.BLOCKER;
            default:
                return Priority.UNSPECIFIED;
        }
    }
}
