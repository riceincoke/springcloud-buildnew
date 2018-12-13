package rabbitmqcore.senders;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 一杯咖啡
 * @desc
 * @createTime 2018-12-11-12:22
 */
@Component
public class MySendMessage {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(){
        String context = "hello"+ new Date();
        System.out.println("context = "+context);
        this.amqpTemplate.convertAndSend("test", context);
    }
}
