package com.zunix.craweler.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zunix.craweler.util.FileUtil;
import com.zunix.dao.jpa.EmailRepository;
import com.zunix.entity.Email;

public class DbLoaderMain {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        EmailRepository emailRepository = context.getBean("emailRepository", EmailRepository.class);

        String dir = "/usr/local/crawl/";
        List<File> files = new ArrayList<File>();

        System.out.println("Start to load files from " + dir);

        Set<String> result = FileUtil.readFileContentAsSet(dir);

        for (String email : result) {
            Email emailObject = new Email(email);
            emailObject.setCreatedBy("sendon1982");
            emailObject.setCreatedTime(new Date());
            try {
                emailRepository.insert(emailObject);
            } catch (Throwable e) {
                System.out.println(e.getMessage());
            }
        }

        for (File file : files) {
            System.out.println(file.getName());
        }

        context.close();
    }


}
