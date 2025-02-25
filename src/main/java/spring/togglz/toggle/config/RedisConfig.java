package spring.togglz.toggle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // TODO: read from application property
        return new LettuceConnectionFactory("localhost", 6379); // Use AWS ElastiCache endpoint in production
    }

    @Bean
    public RedisTemplate<String, Boolean> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Boolean> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}

