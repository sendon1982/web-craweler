package com.zunix.craweler.handler;

import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zunix.dao.jpa.EmailRepository;
import com.zunix.entity.Email;

public class EmailProcessDbHandler implements EmailProcessHandler {

    private static final Logger logger = LoggerFactory.getLogger(EmailProcessDbHandler.class);

    /**
     * Not Autowired from MainApp method invocation, so need to load this bean manually.
     */
    @Autowired
    private EmailRepository emailRepository;

    @SuppressWarnings("resource")
    public EmailProcessDbHandler() {
        logger.debug("Get the spring configure xml from classpath.");

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        emailRepository = context.getBean("emailRepository", EmailRepository.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zunix.craweler.handler.EmailProcessHandler#persist(java.util.Set)
     * 
     * Persist the email list from web page into DB.
     */
    @Override
    public void persist(Set<String> emails) {
        for (String email : emails) {
            Email emailObject = new Email(email);
            emailObject.setCreatedBy("sendon1982");
            emailObject.setCreatedTime(new Date());
            try {
                emailRepository.insert(emailObject);
            } catch (Throwable e) {
                // logger.error("Failed to insert into DB: " + e);
            }
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.zunix.craweler.handler.EmailProcessHandler#persist(java.lang.String)
     */
    @Override
    public void persist(String email) {
        Email emailObject = new Email(email);
        emailObject.setCreatedBy("sendon1982");
        emailObject.setCreatedTime(new Date());
        try {
            emailRepository.insert(emailObject);
        } catch (Throwable e) {
            // logger.error("Failed to insert into DB: " + e);
        }
    }

}
