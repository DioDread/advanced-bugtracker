/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tsvil.bugtracker.entity;

import javax.json.Json;
import javax.json.JsonValue;

/**
 *
 * @author Ales_Tsvil
 */
public class Label {
    private long labelId;
    private String name;
    private int color;
    
    public Label(long labelId, String name, int color) {
        this.labelId = labelId;
        this.name = name;
        this.color = color;
    }

    /**
     * @return the labelId
     */
    public long getLabelId() {
        return labelId;
    }

    /**
     * @param labelId the labelId to set
     */
    public void setLabelId(long labelId) {
        this.labelId = labelId;
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
     * @return the color
     */
    public int getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(int color) {
        this.color = color;
    }
    
    public JsonValue toJson() {
        return Json.createObjectBuilder()
                .add("labelId", labelId)
                .add("name", name)
                .add("color", color)
                .build();
    }
}
