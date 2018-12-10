package hystixcore.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import commoncore.entity.Student;
import commoncore.serviceApi.StudentServiceApi;
import hystixcore.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * @author 一杯咖啡
 * @desc
 * @createTime 2018-11-29-23:48
 */
@RestController
public class HystrixStudentController implements StudentServiceApi {
    @Value("${myInfo.name}")
    private String myInfo;
    private Integer couter = 0;
    @Autowired
    private StudentService studentService;

    @Override
    @HystrixCommand
    public Student findById(@PathVariable(value = "id") int id) {
        return studentService.findById(id);
    }

    @Override
    @HystrixCommand
    public List<Student> findAll() {
        System.out.println(myInfo + "---------被调用" + (++couter) + "次");
        return studentService.findList();
    }

    @Override
    @HystrixCommand
    public boolean deleteOne(@PathVariable(value = "id") int id) {
        return studentService.deleteById(id);
    }

    @Override
    public boolean insertOne(@RequestBody Student student) {
        return studentService.insertOne(student);
    }

    /**
     * desc: 获取信息
     * 配置超时时间
     **/
    @Override
    @HystrixCommand(commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")})
    public Map<String, Object> getInfo() {
        System.out.println(" feign 的 getinfo 方法调用");
        //随机休眠不超过3秒
        int sleepTime = new Random().nextInt(3000);
        System.out.println("hystrixprovider--sleepTime = "+sleepTime);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return studentService.getInfo();
    }
}
