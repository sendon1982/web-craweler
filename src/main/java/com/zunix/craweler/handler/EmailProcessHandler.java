package com.zunix.craweler.handler;

import java.util.Set;

public interface EmailProcessHandler {
    
    /**
     * Persist Email list into all kinds of channels, such as Files, DB and so or
     */
    public void persist(Set<String> emails); 
    
    /**
     * Persist one Email into all kinds of channels, such as Files, DB and so or
     */
    public void persist(String email); 
}
