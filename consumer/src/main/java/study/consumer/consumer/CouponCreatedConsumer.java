package study.consumer.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import study.consumer.domain.Coupon;
import study.consumer.repository.CouponRepository;

@Component
@RequiredArgsConstructor
public class CouponCreatedConsumer {

    private final CouponRepository couponRepository;

    @KafkaListener(topics = "${kafka.topic.coupon-create}", groupId = "${kafka.consumer.group-id}")
    public void listener(Long couponId) {
        couponRepository.save(new Coupon(couponId));
    }
}
