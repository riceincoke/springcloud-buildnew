package providercore.controller;

import commoncore.entity.Student;
import providercore.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @author 一杯咖啡
 * @desc 服务提供者
 * @createTime 2018-11-29-23:48
 */
@RestController
@RequestMapping(value = "/provider")
public class StudentController {
    @Value("${myInfo.name}")
    private String myInfo;

    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/find/{id}")
    public Student findById(@PathVariable(value = "id") int id) {
        return studentService.findById(id);
    }

    @GetMapping(value = "/findAll")
    public List<Student> findAll() {
        System.out.println(myInfo + "---------被调用");
        return studentService.findList();
    }

    @GetMapping(value = "/delete/{id}")
    public boolean deleteOne(@PathVariable(value = "id") int id) {
        return studentService.deleteById(id);
    }

    @PostMapping(value = "/save")
    public boolean insertOne(@RequestBody Student student) {
        return studentService.insertOne(student);
    }

    @GetMapping(value = "/getInfo")
    public Map getInfo() {
        return studentService.getInfo();
    }
}
