package zuulcore;

import com.netflix.zuul.ZuulFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import zuulcore.config.UrlFilter;

/**
 * @author 一杯咖啡
 * @desc zuul启动类
 * @createTime 2018-12-09-19:50
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableZuulProxy
@EnableCircuitBreaker
@EnableDiscoveryClient
public class ZuulApp {
    /**
     * desc: 配置url过滤bean
     **/
    @Bean
    public UrlFilter getUrlFilter(){
        return new UrlFilter();
    }
    public static void main(String[] args){
        SpringApplication.run(ZuulApp.class, args);
    }
}
