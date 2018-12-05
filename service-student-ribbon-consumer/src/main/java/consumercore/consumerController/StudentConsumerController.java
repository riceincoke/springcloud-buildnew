package consumercore.consumerController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import commoncore.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 一杯咖啡
 * @desc 服务消费者
 * @createTime 2018-12-01-15:31
 */
@RestController
@RequestMapping("/student")
public class StudentConsumerController {
   @Value("${info.provider}") private String appName;
    @Value("${appLoaction}") private  String PRE_HOST;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/find/{id}")
    @HystrixCommand(fallbackMethod = "fallBack",commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")})
    public Student getStudent(@PathVariable(value = "id") int id) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return restTemplate.getForObject(PRE_HOST + "/provider/find/" + id, Student.class);
    }
    /**
     * desc:熔断降级
     **/
    public Student fallBack(@PathVariable(value = "id") int id) {
        Student student = new Student();
        student.setId(id);
        student.setName(appName);
        student.setGrade("降级服务消费者返回该数据");
        return student;
    }

    @GetMapping(value = "/findAll")
    public List<Student> findAll() {
        return restTemplate.getForObject(PRE_HOST + "/provider/findAll", List.class);
    }

    @GetMapping(value = "/delete/{id}")
    public boolean deleteOne(@PathVariable(value = "id") int id) {
        return restTemplate.getForObject(PRE_HOST + "/provider/delete/" + id, Boolean.class);
    }

    @PostMapping(value = "/save")
    public boolean insertOne() {
        Student student;
        student = new Student();
        student.setGrade("一年级");
        student.setName("张三");
        return restTemplate.postForObject(PRE_HOST + "/provider/save/", student, Boolean.class);
    }

    @GetMapping(value = "/getInfo")
    public Map<String, Object> getInfo() {
        return restTemplate.getForObject(PRE_HOST + "/provider/getInfo/", Map.class);
    }
}
