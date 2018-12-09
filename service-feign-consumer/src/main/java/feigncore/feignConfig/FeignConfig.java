package feigncore.feignConfig;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RetryRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 一杯咖啡
 * @desc
 * @createTime 2018-12-08-23:22
 */
@Configuration
public class FeignConfig {
    @Bean
    public IRule getIRule() {
        return new RetryRule();
    }
}
