package study.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.consumer.domain.FailedEvent;

@Repository
public interface FailedEventRepository extends JpaRepository<FailedEvent, Long> {

}
