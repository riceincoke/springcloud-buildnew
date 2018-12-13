package feigncore.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import commoncore.entity.Student;
import commoncore.feignService.StudentClientService;
import commoncore.serviceApi.StudentConsumerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 一杯咖啡
 * @desc feign-consumer 自带熔断，如果超时，将自动熔断；
 * 调用hystrix-provider 熔断服务时，如果hystrix-provider返回数据超时熔断，只要在feign-consumer的熔断超时时间内，都可以由hystrix-provider 返回超时时间。
 * 如果，feign-consumer 达到超时时间，将直接由feign-consumer 调用自身的熔断方法。（双重熔断）
 * @createTime 2018-12-07-17:44
 */
@RestController
public class FeignController implements StudentConsumerApi {
    @Autowired
    private StudentClientService studentClientService;
    @Value("${myInfo.name}")
    private String appName;

    @Override
    @HystrixCommand(fallbackMethod = "fallFindById",threadPoolKey = "findOne",groupKey = "feign-consumer")
    public Student providerFindById(@PathVariable(value = "id") int id) {
        System.out.println("feign-consumer--getStudent 方法调用");
        return studentClientService.findById(id);
    }


    /**
     * desc: 查询所有
     **/
    @Override
    @HystrixCommand(fallbackMethod = "fallFindAll",groupKey = "findAll", threadPoolKey = "feign-consumer")
    public List<Student> providerFindAll() {
        System.out.println("feign-consumer--findall 方法调用");
        return studentClientService.findAll();
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallDeleteOne",threadPoolKey = "delete",groupKey = "feign-consumer")
    public boolean providerDeleteOne(@PathVariable(value = "id") int id) {
        System.out.println("feign-consumer--delete 方法调用");
        return studentClientService.deleteOne(id);
    }


    @Override
    @HystrixCommand(fallbackMethod = "fallInsertOne",threadPoolKey = "save",groupKey = "feign-consumer")
    public boolean providerInsertOne() {
        System.out.println("feign-consumer--插入 方法调用");
        Student student;
        student = new Student();
        student.setGrade("一年级");
        student.setName("张三");
        return studentClientService.insertOne(student);
    }

    @Override
    @HystrixCommand(fallbackMethod = "getFallInfo",threadPoolKey = "getInfo",groupKey = "feign-consumer")
    public Map<String, Object> getProviderInfo() {
        return studentClientService.getInfo();
    }

    /**
     * desc: fallbackMethod
     * @Return:
     **/
    public Student fallFindById(int id) {
        System.out.println("feign-consumer---findByid服务降级被调用");
        Student student = new Student();
        student.setId(id);
        student.setGrade("consumer---降级服务返回该数据");
        return student;
    }

    public List<Student> fallFindAll() {
        System.out.println("feign-consumer---findAll服务降级被调用");
        List<Student> mylist = new ArrayList<>();
        Student student = new Student();
        student.setId(500);
        student.setName("feign-consumer---查询所有超时");
        student.setGrade("feign-consumer---熔断服务提供者 降级方法");
        mylist.add(student);
        return mylist;
    }

    public boolean fallDeleteOne(int id) {
        System.out.println("feign-consumer---delete服务降级被调用");
        return false;
    }

    public boolean fallInsertOne() {
        System.out.println("feign-consumer---save服务降级被调用");
        return false;
    }

    public Map<String, Object> getFallInfo() {
        System.out.println("feign-consumer---factory,getInfo服务降级被调用");
        Map<String, Object> map = new HashMap<>(2);
        map.put("code", 500);
        map.put("info", "feign-consumer---系统延迟，稍后重试");
        return map;
    }
}
