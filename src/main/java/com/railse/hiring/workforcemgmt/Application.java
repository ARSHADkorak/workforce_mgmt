package com.railse.hiring.workforcemgmt;

import com.railse.hiring.workforcemgmt.mapper.ITaskManagementMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner testMapper(ITaskManagementMapper mapper) {
        return args -> {
            System.out.println("✅ MapStruct mapper loaded: " + mapper.getClass().getName());
        };
    }
}
