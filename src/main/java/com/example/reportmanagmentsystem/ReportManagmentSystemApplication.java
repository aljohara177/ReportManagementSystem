package com.example.reportmanagmentsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ReportManagmentSystemApplication {


    public static void main(String[] args) {

        SpringApplication.run(ReportManagmentSystemApplication.class, args);
    }

}
