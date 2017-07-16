package io.spring.batch.configuration;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by thanu on 15-07-2017.
 */
//TODO uncomment below
//@Configuration
public class FlowJobConfiguration {


    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step step1() {
        return  stepBuilderFactory.get("step1").tasklet((contribution, chunkContext) -> {
            System.out.println("Step1 from inside flow on foo");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step step2() {
        return  stepBuilderFactory.get("step2").tasklet((contribution, chunkContext) -> {
            System.out.println("Step2 from inside flow on foo");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Flow foo() {
        FlowBuilder<Flow> flowFlowBuilder = new FlowBuilder<>("foo");
        flowFlowBuilder.start(step1()).next(step2()).end();
        return  flowFlowBuilder.build();
    }

//    @Bean // used qualifier in FlowFirstJobConfiguration
//    public Flow foo2() {
//        FlowBuilder<Flow> flowFlowBuilder = new FlowBuilder<>("foo");
//        flowFlowBuilder.start(step1()).next(step2()).end();
//        return  flowFlowBuilder.build();
//    }
}
