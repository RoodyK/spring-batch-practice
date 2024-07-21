package hello.spring.batch.job.validator;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class BatchDateValidator implements JobParametersValidator {

    private final String jobParam = "batchDate";

    @Override
    public void validate(JobParameters jobParameters) throws JobParametersInvalidException {
        String batchDate = jobParameters.getString(jobParam);

        if (!StringUtils.hasText(batchDate)) {
            throw new JobParametersInvalidException("정확한 문자열 형식으로 입력해주세요.");
        }

        try {
            LocalDate.parse(batchDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new JobParametersInvalidException("날짜 포맷이 잘못됐습니다. 가능한 포맷은 yyyy-MM-dd 형식입니다.");
        }
    }
}
