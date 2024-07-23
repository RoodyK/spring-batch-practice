package hello.spring.batch.job.config;

import hello.spring.batch.job.constant.MyStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Getter
public class DateConvertParameter {

    /**
     * 생성자 방식
     */
    private LocalDate batchDate;
    private MyStatus myStatus;

    public DateConvertParameter(String batchDate, MyStatus myStatus) {
        this.batchDate = LocalDate.parse(batchDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.myStatus = myStatus;
    }

    /**
     * Setter 방식
     */
//    private LocalDate batchDate;
//    @Value("#{jobParameters[myState]}")
//    private MyState myState;
//
//    @Value("#{jobParameters[batchDate]}")
//    public void setBatchDate(String batchDate) {
//        this.batchDate = LocalDate.parse(batchDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//    }

}

