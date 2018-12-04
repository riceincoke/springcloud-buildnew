package eurekacore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author 一杯咖啡
 * @desc
 * @createTime 2018-12-01-23:51
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApp {
    public static void main(String[] args){
        SpringApplication.run(EurekaServerApp.class, args);
    }
}
