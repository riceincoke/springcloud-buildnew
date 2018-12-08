package hystixcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @author 一杯咖啡
 * @desc
 * @createTime 2018-11-29-23:53
 */
@SpringBootApplication
@EntityScan(basePackages={"commoncore"})
@EnableEurekaClient
@EnableCircuitBreaker
@EnableHystrix
public class HystrixProviderApp {
    public static void main(String[] args){
        SpringApplication.run(HystrixProviderApp.class, args);
    }
}
