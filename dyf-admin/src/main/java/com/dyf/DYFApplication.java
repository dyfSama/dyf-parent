package com.dyf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dyf.**.mapper")
public class DYFApplication {

    public static void main(String[] args) {
        SpringApplication.run(DYFApplication.class, args);
    }

}

