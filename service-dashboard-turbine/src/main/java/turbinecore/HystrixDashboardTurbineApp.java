package turbinecore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @author 一杯咖啡
 * @desc
 * @createTime 2018-12-03-11:01
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableTurbine
@EnableDiscoveryClient
public class HystrixDashboardTurbineApp {
    public static void main(String[] args){
        SpringApplication.run(HystrixDashboardTurbineApp.class, args);
    }
}
