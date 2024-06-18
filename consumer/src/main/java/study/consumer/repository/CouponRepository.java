package study.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.consumer.domain.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
