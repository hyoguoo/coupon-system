package study.api.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;
    @Value("${redis.key.coupon-count}")
    private String couponCountKey;

    public Long increment() {
        return redisTemplate.opsForValue().increment(couponCountKey);
    }
}
