package spring.togglz.toggle.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.togglz.core.Feature;
import org.togglz.core.manager.FeatureManager;

import java.time.Duration;

@Slf4j
@Repository
public class CachedFeatureRepository {

  private final FeatureManager featureManager;
  private final RedisTemplate<String, Boolean> redisTemplate;

  private static final String FEATURE_TOGGLE_PREFIX = "feature_toggle:";

  public CachedFeatureRepository(
      FeatureManager featureManager, RedisTemplate<String, Boolean> redisTemplate) {
    this.featureManager = featureManager;
    this.redisTemplate = redisTemplate;
  }

  public boolean isActive(Feature feature) {
    String cacheKey = FEATURE_TOGGLE_PREFIX + feature.name();
    Boolean cachedState = redisTemplate.opsForValue().get(cacheKey);
    if (cachedState != null) {
      log.info("Feature {} state from cache is {}", feature.name(), cachedState);
      return cachedState;
    }
    boolean isActive = featureManager.isActive(feature);
    log.info("Feature {} state from store is {}", feature.name(), isActive);
    redisTemplate.opsForValue().set(cacheKey, isActive, Duration.ofSeconds(30));
    return isActive;
  }
}
