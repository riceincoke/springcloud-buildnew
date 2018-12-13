package rabbitmqcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author 一杯咖啡
 * @desc
 * @createTime 2018-12-11-12:35
 */
@SpringBootApplication
@EnableEurekaClient
public class RabbitMqApp {
    public static void main(String[] args){
        SpringApplication.run(RabbitMqApp.class, args);
    }
}
