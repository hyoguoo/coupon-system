package study.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.api.domain.Coupon;
import study.api.producer.CouponCreateProducer;
import study.api.repository.CouponRedisRepository;
import study.api.repository.CouponRepository;

@Service
@RequiredArgsConstructor
public class ApplyService {

    public static final int MAX_COUPON_LIMIT = 100;
    private final CouponRepository couponRepository;
    private final CouponRedisRepository couponRedisRepository;
    private final CouponCreateProducer couponCreateProducer;

    @Transactional
    public void apply(Long userId) {
        long count = couponRepository.count();

        if (count > MAX_COUPON_LIMIT) {
            return;
        }

        couponRepository.save(new Coupon(userId));
    }

    @Transactional
    public void applyWithRedisRepository(Long userId) {
        Long count = couponRedisRepository.increment();

        if (count > MAX_COUPON_LIMIT) {
            return;
        }

        couponRepository.save(new Coupon(userId));
    }

    @Transactional
    public void applyWithKafkaProducer(Long userId) {
        long count = couponRedisRepository.increment();

        if (count > MAX_COUPON_LIMIT) {
            return;
        }

        couponCreateProducer.create(userId);
    }
}
