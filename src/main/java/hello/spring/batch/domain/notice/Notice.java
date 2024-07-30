package hello.spring.batch.domain.notice;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@ToString
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDate regDate;

    @Column(nullable = false)
    private int views;

    @Builder
    private Notice(String title, String content, LocalDate regDate, int views) {
        this.title = title;
        this.content = content;
        this.regDate = regDate;
        this.views = views;
    }
}
