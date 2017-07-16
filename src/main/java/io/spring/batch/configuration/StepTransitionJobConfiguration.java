package io.spring.batch.configuration;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by thanu on 11-07-2017.
 */
//TODO uncomment below
//@Configuration
//@EnableBatchProcessing
public class StepTransitionJobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step step1() {
        return  stepBuilderFactory.get("step1").tasklet((contribution, chunkContext) -> {
            System.out.println(">>> This is step1");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step step2() {
        return  stepBuilderFactory.get("step2").tasklet((contribution, chunkContext) -> {
            System.out.println(">>> This is step2");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step step3() {
        return  stepBuilderFactory.get("step3").tasklet((contribution, chunkContext) -> {
            System.out.println(">>> This is step3");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Job transitionJobSimpleNext() {
//        return  jobBuilderFactory.get("transitionJobNext")
//                .start(step1())
//                .next(step2())
//                .next(step3())
//                .build();

//        return  jobBuilderFactory.get("transitionJobNext")
//                .start(step1())
//                .next(step3())
//                .next(step2())
//                .next(step2())
//                .build();

        return  jobBuilderFactory.get("transitionJobNext")
                .start(step1())
                .on(BatchStatus.COMPLETED.toString()).to(step2())
                .from(step2()).on(BatchStatus.COMPLETED.toString()).to(step3())
                .from(step2()).end()
                .build();

    }
}
