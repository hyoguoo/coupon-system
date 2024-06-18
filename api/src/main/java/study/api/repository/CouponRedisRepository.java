package study.api.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponRedisRepository {

    private static final String COUPON_COUNT = "coupon_count";
    private final RedisTemplate<String, String> redisTemplate;

    public Long increment() {
        return redisTemplate.opsForValue().increment(COUPON_COUNT);
    }
}
