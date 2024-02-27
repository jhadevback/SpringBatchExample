package com.joaquin.batch.springbatch.config;

import com.joaquin.batch.springbatch.steps.ItemDescompressStep;
import com.joaquin.batch.springbatch.steps.ItemProcessorStep;
import com.joaquin.batch.springbatch.steps.ItemReaderStep;
import com.joaquin.batch.springbatch.steps.ItemWriteStep;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    @JobScope
    public ItemDescompressStep itemDescompressStep(){
        return new ItemDescompressStep();
    }

    @Bean
    @JobScope
    public ItemReaderStep itemReaderStep(){
        return new ItemReaderStep();
    }

    @Bean
    @JobScope
    public ItemProcessorStep itemProcessorStep(){
        return new ItemProcessorStep();
    }

    @Bean
    @JobScope
    public ItemWriteStep itemWriteStep(){
        return new ItemWriteStep();
    }

    @Bean
    public Step descompressFileStep(){
        return stepBuilderFactory.get("itemDescompressStep")
                .tasklet(itemDescompressStep())
                .build();
    }

    @Bean
    public Step readFileStep(){
        return stepBuilderFactory.get("itemReaderStep")
                .tasklet(itemReaderStep())
                .build();
    }

    @Bean
    public Step processDataStep(){
        return stepBuilderFactory.get("itemProcessStep")
                .tasklet(itemReaderStep())
                .build();
    }

    @Bean
    public Step writerDataStep(){
        return stepBuilderFactory.get("itemWriterStep")
                .tasklet(itemWriteStep())
                .build();
    }

    @Bean
    public Job readCSVJob(){
        return jobBuilderFactory.get("readCSVJob")
                .start(descompressFileStep())
                .next(readFileStep())
                .next(processDataStep())
                .next(writerDataStep())
                .build();
    }

}
