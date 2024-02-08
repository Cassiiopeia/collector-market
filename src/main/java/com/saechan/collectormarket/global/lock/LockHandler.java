package com.saechan.collectormarket.global.lock;

import com.saechan.collectormarket.global.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LockHandler {
  private final RedissonClient redissonClient;
  public <T> T runWithLock(String key, long waitTime, long leaseTime, Supplier<T> execute){
    RLock lock = redissonClient.getLock(key);
    try{
      if(!lock.tryLock(waitTime,leaseTime, TimeUnit.SECONDS)){
        // 락 획득 실패
        log.info("lock get failed {}", LocalDateTime.now());
        throw new LockException(ErrorCode.LOCK_GET_FAILED);
      }
      // 락 획득 성공 -> 로직 실행
      log.info("lock get success {}", LocalDateTime.now());
      return execute.get();
    } catch (InterruptedException e) {
      // 현재 스레드 중단 요청
      Thread.currentThread().interrupt();
      throw new LockException(ErrorCode.LOCK_INTERRUPTED);
    } finally {
      // 락 해제
      log.info("lock release {}", LocalDateTime.now());
      lock.unlock();
    }
  }
}
