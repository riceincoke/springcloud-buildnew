package zuulcore;

import com.netflix.zuul.ZuulFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
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
    /*@Bean
    public PatternServiceRouteMapper serviceRouteMapper () {
        return new PatternServiceRouteMapper(
                "(?<name>.+)-(?<version>v.+$)",
                "${version}/${name}");
    }*/
   /* @Bean
    @RefreshScope
    @ConfigurationProperties("zuul")
    public ZuulProperties zuulProperties(){
        return new ZuulProperties();
    }*/
}
