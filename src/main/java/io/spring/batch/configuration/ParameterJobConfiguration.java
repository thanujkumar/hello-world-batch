package io.spring.batch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by thanu on 16-07-2017.
 */
@Configuration
public class ParameterJobConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope //Lazily instantiated
    public Tasklet helloWordTasklet(@Value("#{jobParameters['message']}") String message) {
        return  (contribution, chunkContext) -> {
            System.out.println(">>>> job parameter -> "+ message);
            return RepeatStatus.FINISHED;
        } ;
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("parameterStep")
                .tasklet(helloWordTasklet(null))
                .build();
    }

    @Bean
    public Job parameterJob() {
        return  jobBuilderFactory.get("parameterJob").start(step1()).build();
    }
}
