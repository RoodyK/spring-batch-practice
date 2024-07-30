package hello.spring.batch.job.config;

import hello.spring.batch.domain.notice.Notice;
import hello.spring.batch.domain.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class NoticeJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final NoticeRepository noticeRepository;

    private final int chunkSize = 5;

    @Bean
    public Job parameterJob() {
        return jobBuilderFactory.get("noticeJob")
                .incrementer(new RunIdIncrementer())
                .start(parameterStep())
                .build();
    }

    @JobScope
    @Bean
    public Step parameterStep() {
        return stepBuilderFactory.get("noticeStep")
                .<Notice, Notice>chunk(chunkSize)
                .reader(parameterReader(null))
                .writer(parameterWriter())
                .build();
    }

    @StepScope
    @Bean
    public ItemReader<Notice> parameterReader(
            @Value("#{jobParameters[batchDate]}") String batchDate
    ) {
        LocalDate regDate = LocalDate.parse(batchDate);

        return new RepositoryItemReaderBuilder<Notice>()
                .name("parameterReader")
                .repository(noticeRepository)
                .methodName("findByRegDate")
                .pageSize(chunkSize)
                .arguments(Collections.singletonList(regDate))
                .sorts(Collections.singletonMap("views", Sort.Direction.DESC))
                .build();
    }

    @StepScope
    @Bean
    public ItemWriter<Notice> parameterWriter() {
        return list -> list.forEach(item -> log.info("{}", item));
    }

}
