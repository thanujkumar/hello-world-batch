package io.spring.batch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * Created by thanu on 16-07-2017.
 */
//TODO uncomment below
//@Configuration
public class SplitParallelJobConfiguration {

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Bean
    public Tasklet tasklet() {
        return new CountingTasklet();
    }

    @Bean
    public Flow flow1() {
        return  new FlowBuilder<Flow>("flow1")
                .start(stepBuilderFactory.get("Flow1 Step1")
                        .tasklet(tasklet()).build())
                .build();
    }

    @Bean
    public Flow flow2() {
        return  new FlowBuilder<Flow>("flow2")
                .start(stepBuilderFactory.get("Flow2 Step2")
                        .tasklet(tasklet()).build())
                .next(stepBuilderFactory.get("Flow2 Step3")
                        .tasklet(tasklet()).build())
                .build();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("splitParallelJob")
                .start(flow1())
                .split(new SimpleAsyncTaskExecutor()).add(flow2())
                .end()
                .build();
    }

    private static class CountingTasklet implements Tasklet {
        @Override
        public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
            System.out.println(String.format("%s has been executed on thread %s", chunkContext.getStepContext().getStepName(), Thread.currentThread()));
            return RepeatStatus.FINISHED;
        }
    }
}
