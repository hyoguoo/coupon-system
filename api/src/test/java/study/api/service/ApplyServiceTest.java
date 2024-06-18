package study.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static study.api.service.ApplyService.MAX_COUPON_LIMIT;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.api.repository.CouponRedisRepository;
import study.api.repository.CouponRepository;

@SpringBootTest
class ApplyServiceTest {

    @Autowired
    private ApplyService applyService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponRedisRepository couponRedisRepository;


    @BeforeEach
    void setUp() {
        couponRepository.deleteAll();
        couponRedisRepository.deleteAll();
    }

    @Test
    @DisplayName("한 명만 쿠폰을 발급한다.")
    void apply_with_one_user() {
        // given
        final long userId = 1L;

        // when
        applyService.apply(userId);

        // then
        assertThat(couponRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("여러 명이 쿠폰을 발급한다.")
    void apply_with_multiple_users() throws InterruptedException {
        // given
        final int totalUsers = 1000;
        final int threadCount = 32;

        // when
        CountDownLatch countDownLatch;

        try (ExecutorService executorService = Executors.newFixedThreadPool(threadCount)) {
            countDownLatch = new CountDownLatch(totalUsers);

            for (int i = 0; i < totalUsers; i++) {
                long userId = i;
                executorService.submit(() -> {
                    try {
                        applyService.apply(userId);
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }
        }

        countDownLatch.await();

        // then
        assertThat(couponRepository.count()).isNotEqualTo(MAX_COUPON_LIMIT);
    }

    @Test
    @DisplayName("여러 명이 쿠폰을 발급한다. (Redis)")
    void apply_with_multiple_users_with_redis() throws InterruptedException {
        // given
        final int totalUsers = 1000;
        final int threadCount = 32;

        // when
        CountDownLatch countDownLatch;

        try (ExecutorService executorService = Executors.newFixedThreadPool(threadCount)) {
            countDownLatch = new CountDownLatch(totalUsers);

            for (int i = 0; i < totalUsers; i++) {
                long userId = i;
                executorService.submit(() -> {
                    try {
                        applyService.applyWithRedisRepository(userId);
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }
        }

        countDownLatch.await();

        // then
        assertThat(couponRepository.count()).isEqualTo(MAX_COUPON_LIMIT);
    }

    @Test
    @DisplayName("여러 명이 쿠폰을 발급한다. (Kafka)")
    void apply_with_multiple_users_with_kafka() throws InterruptedException {
        // given
        final int totalUsers = 3000;
        final int threadCount = 32;

        // when
        CountDownLatch countDownLatch;

        try (ExecutorService executorService = Executors.newFixedThreadPool(threadCount)) {
            countDownLatch = new CountDownLatch(totalUsers);

            for (int i = 0; i < totalUsers; i++) {
                long userId = i;
                executorService.submit(() -> {
                    try {
                        applyService.applyWithKafkaProducer(userId);
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }
        }

        countDownLatch.await();

        // then
        assertThat(couponRepository.count()).isEqualTo(MAX_COUPON_LIMIT);
    }
}
