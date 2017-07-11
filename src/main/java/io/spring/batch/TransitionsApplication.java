package io.spring.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by thanu on 11-07-2017.
 */
@SpringBootApplication
@EnableBatchProcessing
public class TransitionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransitionsApplication.class, args);
    }
}
