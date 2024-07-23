
package hello.spring.batch.job.config;

import hello.spring.batch.job.constant.MyStatus;
import hello.spring.batch.job.validator.BatchDateValidator;
import hello.spring.batch.job.validator.MyStateValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class HelloJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DateConvertParameter jobParameter;

    // JobParameters Constructor 주입을 빈 생성 및 스코프 지정
    @Bean
    @JobScope
    public DateConvertParameter customParameter(
            @Value("#{jobParameters[myState]}") MyStatus myStatus,
            @Value("#{jobParameters[batchDate]}") String batchDate
    ) {
        return new DateConvertParameter(batchDate, myStatus);
    }

    // JobParameters Setter 주입을 빈 생성 및 스코프 지정
//    @Bean
//    @JobScope
//    public DateConvertParameter customParameter() {
//        return new DateConvertParameter();
//    }

    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("helloJob")
                .incrementer(new RunIdIncrementer()) // JobParameters 고유한 run.id 값 지정
                .validator(customValidator())
                .start(helloStep())
                .build();
    }

    public JobParametersValidator customValidator() {
        CompositeJobParametersValidator validator = new CompositeJobParametersValidator();
        validator.setValidators(Arrays.asList(
                new BatchDateValidator(),
                new MyStateValidator()
        ));

        return validator;
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
    public Tasklet helloTasklet(
            @Value("#{jobParameters[batchDate]}") String batchDate
    ) {
        return (stepContribution, chunkContext) -> {
            log.info("[helloTasklet] start");
            log.info("[helloTasklet] jobParameters.batchDate = {}", batchDate);
            log.info("[helloTasklet] jobParameters.batchDate = {}", jobParameter.getBatchDate());
            log.info("[helloTasklet] jobParameters.myState = {}", jobParameter.getMyStatus());
            return RepeatStatus.FINISHED;
        };
    }
}

