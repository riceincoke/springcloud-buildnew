package providercore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author 一杯咖啡
 * @desc
 * @createTime 2018-11-29-23:53
 */
@SpringBootApplication
@EntityScan(basePackages={"commoncore.entity"})
@EnableEurekaClient
public class ProviderApp {
    public static void main(String[] args){
        SpringApplication.run(ProviderApp.class, args);
    }
}
