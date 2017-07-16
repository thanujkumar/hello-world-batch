package io.spring.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by thanu on 16-07-2017.
 */
@SpringBootApplication
@EnableBatchProcessing
public class NestedApplication {

    public static void main(String[] args) {
        SpringApplication.run(NestedApplication.class, args);
    }

}
