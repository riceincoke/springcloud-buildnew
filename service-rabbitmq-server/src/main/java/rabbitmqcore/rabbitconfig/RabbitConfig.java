package rabbitmqcore.rabbitconfig;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author 一杯咖啡
 * @desc rabbit Queue配置
 * @createTime 2018-12-11-12:33
 */
@Configuration
public class RabbitConfig {
    @Bean
    public Queue testQueue(){
        return new Queue("test");
    }
}
