package study.api.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AppliedUserRepository {

    private final RedisTemplate<String, String> redisTemplate;
    @Value("${redis.key.applied-user}")
    private String appliedUserKey;

    public Long add(Long userId) {
        return redisTemplate.opsForSet().add(appliedUserKey, String.valueOf(userId));
    }

    public void deleteAll() {
        redisTemplate.delete(appliedUserKey);
    }
}
