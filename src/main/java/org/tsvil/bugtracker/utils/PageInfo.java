package org.tsvil.bugtracker.utils;

public class PageInfo {
    
    public int limit;
    public int offset;
    
    public PageInfo(int limit, int offset) {
        this.limit = limit;
        this.offset = offset;
    }
}
