package commoncore.serviceApi;

import commoncore.entity.Student;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 一杯咖啡
 * @desc student 服务提供接口
 * @createTime 2018-12-08-15:57
 */
@RequestMapping(value = "provider")
public interface StudentServiceApi {
    @GetMapping(value = "find/{id}")
    @ResponseBody
    Student findById(@PathVariable(value = "id") int id);

    @GetMapping(value = "findAll")
    @ResponseBody
    List<Student> findAll();

    @GetMapping(value = "delete/{id}")
    @ResponseBody
    boolean deleteOne(@PathVariable(value = "id") int id);

    @PostMapping(value = "save")
    @ResponseBody
    boolean insertOne(@RequestBody Student student);

    @GetMapping(value = "getInfo")
    @ResponseBody
    Map getInfo();
}
