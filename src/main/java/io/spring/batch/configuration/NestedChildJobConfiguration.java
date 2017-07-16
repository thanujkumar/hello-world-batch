package io.spring.batch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by thanu on 16-07-2017.
 */
//TODO commented to run different example, uncomment when running HelloWordApplication
//@Configuration
public class NestedChildJobConfiguration {

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Bean
    public Step step1a() {
        return stepBuilderFactory.get("step1a")
                .tasklet((contribution, chunkContext) -> {
                        System.out.println("\t>>  This is step 1a from child  -> " + Thread.currentThread());
                        return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Job childJob() {
        return jobBuilderFactory.get("childJob")
                .start(step1a())
                .build();
    }

}
