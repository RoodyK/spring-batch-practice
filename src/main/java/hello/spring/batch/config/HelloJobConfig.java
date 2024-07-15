
package hello.spring.batch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class HelloJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("helloJob")
                //.incrementer(new RunIdIncrementer()) // JobParameters 고유한 run.id 값 지정
                .start(helloStep())
                .build();
    }

    @JobScope
    @Bean
    public Step helloStep() {
        return stepBuilderFactory.get("helloStep")
                .tasklet(helloTasklet(null))
                .build();
    }

    @StepScope
    @Bean
    public Tasklet helloTasklet(@Value("#{jobParameters[batchDate]}") String batchDate) {
        return (stepContribution, chunkContext) -> {
            log.info("[helloTasklet] start");
            log.info("[helloTasklet] jobParameters.batchDate = {}", batchDate);
            return RepeatStatus.FINISHED;
        };
    }
}

