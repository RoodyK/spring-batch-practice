package hello.spring.batch.domain.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Page<Notice> findByRegDate(LocalDate regDate, Pageable pageable);
}
