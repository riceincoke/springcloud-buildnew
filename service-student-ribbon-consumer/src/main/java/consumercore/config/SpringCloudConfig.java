package consumercore.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RetryRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author 一杯咖啡
 * @desc
 * @createTime 2018-12-01-15:29
 */
@Configuration
public class SpringCloudConfig {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    /*@Bean
    public IPing ribbonIPing(){
        return  new PingUrl();
    }*/
    /**
     * desc: 自定义重试策略负载均衡
     **/
    @Bean
    public IRule getIRule(){
        return new RetryRule();
    }
}
