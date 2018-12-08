package feigncore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 一杯咖啡
 * @desc
 * @createTime 2018-12-07-17:41
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableEurekaClient
@EnableFeignClients(basePackages = "commoncore.feignService")
@ComponentScan(basePackages = {"commoncore","feigncore"})
@EnableCircuitBreaker
public class FeignConsumer {
    public static void main(String[] args){
        SpringApplication.run(FeignConsumer.class, args);
    }
}
