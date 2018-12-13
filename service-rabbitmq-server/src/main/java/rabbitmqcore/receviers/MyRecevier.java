package rabbitmqcore.receviers;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author 一杯咖啡
 * @desc
 * @createTime 2018-12-11-12:23
 */
@Component
@RabbitListener(queues = "test")
public class MyRecevier {

    @RabbitHandler
    public void process(String context){
        System.out.println("接收到Recevier +"+context);
    }
}
