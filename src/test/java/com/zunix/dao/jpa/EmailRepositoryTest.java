package com.zunix.dao.jpa;


import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zunix.entity.Email;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-dao.xml"})
public class EmailRepositoryTest {

    private static final String TEST_EMAIL = "test_search@gmail.com";

    @Autowired
    EmailRepository emailRepository;

    @Before
    public void setUp() throws Exception {
        Email email = new Email();
        email.setEmail(TEST_EMAIL);
        email.setCreatedBy("JM");
        email.setCreatedTime(new Date());

        emailRepository.insert(email);
    }

    @After
    public void tearDown() throws Exception {
        Email email = new Email();
        email.setEmail(TEST_EMAIL);

        emailRepository.delete(emailRepository.getEmailByEmail(email));
    }

    @Test
    public void testInsert() {
        Email email = new Email();
        email.setEmail("test@gmail.com");
        email.setCreatedBy("JM");
        email.setCreatedTime(new Date());

        emailRepository.insert(email);

        Email result = emailRepository.getEmailByEmail(email);
        Assert.assertEquals("test@gmail.com", result.getEmail());
        emailRepository.delete(result);
        
        result = emailRepository.getEmailByEmail(new Email("test@gmail.com"));
        Assert.assertEquals(null, result.getEmail());
    }

    @Test
    public void testGetEmailByEmail() {
        Email email = new Email();
        email.setEmail(TEST_EMAIL);
        Email result = emailRepository.getEmailByEmail(email);

        Assert.assertEquals(TEST_EMAIL, result.getEmail());
    }

    @Test
    public void testDelete() {
        Email result = emailRepository.getEmailByEmail(new Email(TEST_EMAIL));
        Assert.assertEquals(TEST_EMAIL, result.getEmail());
    }
}
