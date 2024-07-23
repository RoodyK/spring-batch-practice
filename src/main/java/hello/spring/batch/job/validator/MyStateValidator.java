package hello.spring.batch.job.validator;

import hello.spring.batch.job.config.DateConvertParameter;
import hello.spring.batch.job.constant.MyStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.util.StringUtils;

public class MyStateValidator implements JobParametersValidator {

    private final String jobParam = "myState";

    @Override
    public void validate(JobParameters jobParameters) throws JobParametersInvalidException {
        String myState = jobParameters.getString(jobParam);

        if (!StringUtils.hasText(myState)) {
            throw new JobParametersInvalidException("정확한 문자열 형식으로 입력해주세요.");
        }

        try {
            MyStatus.valueOf(myState);
        } catch (IllegalArgumentException e) {
            throw new JobParametersInvalidException("상태를 다시 확인하세요");
        }
    }
}
