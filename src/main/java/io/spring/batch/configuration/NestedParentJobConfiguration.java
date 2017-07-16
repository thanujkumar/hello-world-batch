package io.spring.batch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Created by thanu on 16-07-2017.
 */
//TODO commented to run different example, uncomment when running HelloWordApplication
// uncomment in application.properties #spring.batch.job.names=parentJob
//@Configuration
public class NestedParentJobConfiguration {

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    private Job childJob;

    @Autowired
    private JobLauncher jobLauncher; //Already configured as part of @EnableBatchProcessing annotation

    @Bean
    public Step step1() {
        return  stepBuilderFactory.get("parentStep1")
                .tasklet((contribution, chunkContext) -> {
                      System.out.println(">> This is step1 from parent -> "+ Thread.currentThread());
                      return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Job parentJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        Step childJobStep = new JobStepBuilder(new StepBuilder("childJobStep"))
                .job(childJob)
                .launcher(jobLauncher)
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .build();
         return  jobBuilderFactory.get("parentJob")
                 .start(step1())
                 .next(childJobStep)
                 .build();
    }
}
