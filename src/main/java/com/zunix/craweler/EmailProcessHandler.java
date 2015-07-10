package com.zunix.craweler;

public interface EmailProcessHandler {
    
    /**
     * Persist Email into all kinds of channels, such as Files, DB and so or
     */
    public void persist(); 
}
