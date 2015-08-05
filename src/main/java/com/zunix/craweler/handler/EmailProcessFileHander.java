package com.zunix.craweler.handler;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailProcessFileHander implements EmailProcessHandler {

    private static final Logger logger = LoggerFactory.getLogger(EmailProcessFileHander.class);
    
    private String url;
    
    private File storageFolder;
    
    @Override
    public void persist(Set<String> emails) {
        // get a unique name for storing this image
        String extension = url.substring(url.lastIndexOf('.'));
        String hashedName = UUID.randomUUID() + extension;

        // store image
        String filename = storageFolder.getAbsolutePath() + "/" + hashedName;
        try
        {
            FileUtils.writeLines(new File(filename), emails);
            
            logger.info("Stored: {}", url);
        }
        catch (IOException iox)
        {
            logger.error("Failed to write file: " + filename, iox);
        }
        
    }
    
    /**
     * Persist one Email into all kinds of channels, such as Files, DB and so or
     */
    @Override
    public void persist(String email) {
        // get a unique name for storing this image
        String extension = url.substring(url.lastIndexOf('.'));
        String hashedName = UUID.randomUUID() + extension;

        // store image
        String filename = storageFolder.getAbsolutePath() + "/" + hashedName;
        try
        {
            FileUtils.writeLines(new File(filename), Arrays.asList(email));
            
            logger.info("Stored: {}", url);
        }
        catch (IOException iox)
        {
            logger.error("Failed to write file: " + filename, iox);
        }
    }
   

}
