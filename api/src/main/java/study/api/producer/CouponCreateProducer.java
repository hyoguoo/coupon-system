package study.api.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponCreateProducer {

    private final KafkaTemplate<String, Long> kafkaTemplate;
    @Value("${kafka.topic.coupon-create}")
    private String couponCreateTopic;

    public void create(Long userId) {
        kafkaTemplate.send(couponCreateTopic, userId);
    }
}
