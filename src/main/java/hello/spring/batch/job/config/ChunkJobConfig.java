package hello.spring.batch.job.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ChunkJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job noticeJob() {
        return jobBuilderFactory.get("chunkJob")
                .incrementer(new RunIdIncrementer())
                .start(noticeStep())
                .build();
    }

    @JobScope
    @Bean
    public Step noticeStep() {
        return stepBuilderFactory.get("chunkStep")
                .<String, String>chunk(5)
                .reader(noticeItemReader())
                .processor(noticeItemProcessor())
                .writer(noticeItemWriter())
                .build();
    }

    @StepScope
    @Bean
    public ItemReader<String> noticeItemReader() {
        log.info("[noticeItemReader] Item Read");
        return new ListItemReader<>(getItems());
    }

    @StepScope
    @Bean
    public ItemProcessor<String, String> noticeItemProcessor() {
        return item -> {
            log.info("[noticeItemProcessor] Item Process");
            return "Updated " + item;
        };
    }

    @StepScope
    @Bean
    public ItemWriter<String> noticeItemWriter() {
        return list -> {
            list.forEach(item -> log.info("[noticeItemWriter] Item Write. Item = {}", item));
        };
    }

    private List<String> getItems() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            list.add("Item" + i);
        }

        return list;
    }
}

