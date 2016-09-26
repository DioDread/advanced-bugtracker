/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tsvil.bugtracker.utils;

import org.tsvil.bugtracker.entity.Priority;
import org.tsvil.bugtracker.entity.State;

/**
 *
 * @author Ales_Tsvil
 */
public class EntityUtils {
    
    public State resolveState(int stateId) {
        return State.OPEN;
    }
    
    public Priority resolvePriority(int priorityId) {
        return Priority.UNSPECIFIED;
    }
}
