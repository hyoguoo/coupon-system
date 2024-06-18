package study.consumer.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CouponCreatedConsumer {

    @KafkaListener(topics = "${kafka.topic.coupon-create}", groupId = "${kafka.consumer.group-id}")
    public void listener(Long couponId) {
        System.out.println("Coupon created: " + couponId);
    }
}
