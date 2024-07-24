package hello.spring.batch.job.validator;

import hello.spring.batch.job.constant.MyStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.util.StringUtils;

public class MyStatusValidator implements JobParametersValidator {

    private final String jobParam = "myStatus";

    @Override
    public void validate(JobParameters jobParameters) throws JobParametersInvalidException {
        String myStatus = jobParameters.getString(jobParam);

        if (!StringUtils.hasText(myStatus)) {
            throw new JobParametersInvalidException("정확한 문자열 형식으로 입력해주세요.");
        }

        try {
            MyStatus.valueOf(myStatus);
        } catch (IllegalArgumentException e) {
            throw new JobParametersInvalidException("상태를 다시 확인하세요");
        }
    }
}
