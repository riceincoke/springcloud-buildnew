package feigncore.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import commoncore.entity.Student;
import commoncore.feignService.StudentClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author 一杯咖啡
 * @desc
 * @createTime 2018-12-07-17:44
 */
@RestController
public class FeignController {
    @Autowired
    private StudentClientService studentClientService;
    @Value("${myInfo.name}")
    private String appName;

    @GetMapping("find/{id}")
    @HystrixCommand
    public Student getStudent(@PathVariable(value = "id") int id) {
        System.out.println("getStudent 方法调用");
        return studentClientService.findById(id);
    }


    /**
     * desc: 查询所有
     **/
    @GetMapping(value = "findAll")
    @HystrixCommand(commandKey = "find_all", groupKey = "consumer", threadPoolKey = "findAll")
    public List<Student> findAll() {
        System.out.println("findall 方法调用");
        return studentClientService.findAll();
    }

    @GetMapping(value = "delete/{id}")
    @HystrixCommand
    public boolean deleteOne(@PathVariable(value = "id") int id) {
        System.out.println("delete 方法调用");
        return studentClientService.deleteOne(id);
    }

    @GetMapping(value = "save")
    @HystrixCommand
    //@CacheRemove(cacheKeyMethod = "findAllByAsync",commandKey = )
    public boolean insertOne() {
        System.out.println("插入 方法调用");
        Student student;
        student = new Student();
        student.setGrade("一年级");
        student.setName("张三");
        return studentClientService.insertOne(student);
    }

    @GetMapping(value = "getInfo")
    @HystrixCommand(threadPoolKey = "getInfo",groupKey = "feign-consumer",commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "6000")})
    public Map<String, Object> getInfo() {
        return studentClientService.getInfo();
    }
}
