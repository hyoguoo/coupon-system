package study.consumer.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import study.consumer.domain.Coupon;
import study.consumer.domain.FailedEvent;
import study.consumer.repository.CouponRepository;
import study.consumer.repository.FailedEventRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponCreatedConsumer {

    private final CouponRepository couponRepository;
    private final FailedEventRepository failedEventRepository;

    @KafkaListener(topics = "${kafka.topic.coupon-create}", groupId = "${kafka.consumer.group-id}")
    public void listener(Long couponId) {
        try {
            couponRepository.save(new Coupon(couponId));
        } catch (Exception e) {
            log.error("failed to create coupon:: couponId={}", couponId, e);
            failedEventRepository.save(new FailedEvent(couponId));
        }
    }
}
