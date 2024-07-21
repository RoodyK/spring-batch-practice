package hello.spring.batch.job.config;

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
    private MyState myState;

    public DateConvertParameter(String batchDate, MyState myState) {
        this.batchDate = LocalDate.parse(batchDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.myState = myState;
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

    @NoArgsConstructor
    @Getter
    public enum MyState {
        BLOG, REST, WORK;

        public void checkState(String myState) {
            for (MyState value : MyState.values()) {
                if (value.name().equals(myState)) {
                    return;
                }
            }

            throw new IllegalArgumentException("잘못된 상태입니다.");
        }
    }
}

