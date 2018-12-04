package commoncore.feginService;

import commoncore.entity.Student;
import commoncore.hystrix.StudentFallBackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @author 一杯咖啡
 * @desc feign 客户端  value,name 指定服务提供者的application.name
 * @createTime 2018-12-01-22:48
 */
@FeignClient(name = "student-provider", fallbackFactory = StudentFallBackFactory.class)
public interface StudentClientService {
    @GetMapping("/provider/find/{id}")
    Student getStudent(@PathVariable(value = "id") int id);

    @GetMapping(value = "/provider/find")
    List<Student> findAll();

    @GetMapping(value = "/provider/delete/{id}")
    boolean deleteOne(@PathVariable(value = "id") int id);

    @PostMapping(value = "/provider/save")
    boolean insertOne(Student student);

    @GetMapping(value = "/provider/getInfo")
    Map<String, Object> getInfo();
}
