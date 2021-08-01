package com.liyu.breeze;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


/**
 * main class
 *
 * @author gleiyu
 */
@EnableCaching
@SpringBootApplication
public class BreezeApplication {
    public static void main(String[] args) {
        SpringApplication.run(BreezeApplication.class, args);
    }
}
