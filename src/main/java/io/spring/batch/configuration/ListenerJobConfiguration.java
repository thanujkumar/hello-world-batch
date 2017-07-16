package io.spring.batch.configuration;

import io.spring.batch.listener.MyChunkListener;
import io.spring.batch.listener.MyJobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;


/**
 * Created by thanu on 16-07-2017.
 */
//TODO uncomment below
//@Configuration
public class ListenerJobConfiguration {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Bean
    public ItemReader<String> reader() {
        return  new ListItemReader<>(Arrays.asList("one","two","three"));
    }

    @Bean
    public ItemWriter<String> writer() {
        return items -> {
            for (String s : items) {
                System.out.println(">> chunk writing item -> "+ s);
            }
        };
//        return new ItemWriter<String>() {
//            @Override
//            public void write(List<? extends String> items) throws Exception {
//                for (String s : items) {
//                    System.out.println(">> chunk writing item -> "+ s);
//                }
//            }
//        };

    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("chunkStep1")
                .<String, String>chunk(2)
                .faultTolerant()
                .listener(new MyChunkListener())
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean
    public Job listenerJob() {
        return jobBuilderFactory.get("listenerJob")
                .start(step1())
                .listener(new MyJobListener())
                .build();

    }
}
