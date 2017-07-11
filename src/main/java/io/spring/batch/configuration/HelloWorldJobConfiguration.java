package io.spring.batch.configuration;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.support.DatabaseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Isolation;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by thanuj on 11-07-2017.
 */
//TODO commented to run different example
//@Configuration
//@EnableBatchProcessing
public class HelloWorldJobConfiguration {

    @Autowired
    private  JobBuilderFactory jobBuilderFactory;

    @Autowired
    private  StepBuilderFactory stepBuilderFactory;


    //TODO if application.properties doesn't have oracle details and pom.xml has no dependency specified for "spring-boot-starter-jdbc" with oracle driver in classpath
    //TODO uncomment below code to create custom JobRepository, DataSource and TransactionManager (note: Still oracle drivers required in classpath)

/*    @Bean
    public JobRepository jobRepository(DataSource dataSource) throws Exception {
        final JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager(dataSource));
        factory.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
        factory.setDatabaseType(DatabaseType.ORACLE.getProductName());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Primary
    public  DataSource dataSource() throws SQLException {
        PoolDataSource dataSource = PoolDataSourceFactory.getPoolDataSource();
        dataSource.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
        dataSource.setURL("jdbc:oracle:thin:@//localhost:1521/orcl");
        dataSource.setUser("SpringBatch");
        dataSource.setPassword("app");
        dataSource.setMinPoolSize(3);
        dataSource.setMaxPoolSize(5);
        dataSource.setFastConnectionFailoverEnabled(true);
        dataSource.setONSConfiguration(null);
        dataSource.setSQLForValidateConnection("select 1 from dual");
        //return DataSourceBuilder.create().driverClassName().url().username().password().build();
        return  dataSource;
    }
 */

    @Bean
    public Step step1() {

        return stepBuilderFactory.get("step1").tasklet((contribution, chunkContext) -> {
            System.out.println("Hello World");
            return RepeatStatus.FINISHED;
        }).build();
//        return stepBuilderFactory.get("step1").tasklet(new Tasklet() {
//            @Override
//            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                System.out.println("Hello World");
//                return RepeatStatus.FINISHED;
//            }
//        }).build();
    }

    @Bean
    public Job helloWorldJob() {
          return  jobBuilderFactory.get("helloWorldJob").start(step1()).build();
    }

}
