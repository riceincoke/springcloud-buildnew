package hystixcore.controller;

import commoncore.entity.Student;
import hystixcore.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @author 一杯咖啡
 * @desc
 * @createTime 2018-11-29-23:48
 */
@RestController
@RequestMapping(value = "/provider")
public class HystrixStudentController {
    @Value("${myinfo.name}") private String myInfo;
    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/find/{id}")
    public Student findById(@PathVariable(value = "id") int id) {
        return studentService.findById(id);
    }
    @GetMapping(value = "/findAll")
    public List<Student> findAll(){
        System.out.println(myInfo+"---------被调用");
        return studentService.findList();
    }
    @GetMapping(value = "/delete/{id}")
    public boolean deleteOne(@PathVariable(value = "id") int id){
        return studentService.deleteById(id);
    }
    @PostMapping(value = "/save")
    public boolean insertOne(@RequestBody Student student){
        return studentService.insertOne(student);
    }

    /**
     * desc: 获取信息
     * 配置超时时间
     **/
    @GetMapping(value = "/getInfo")
    public Map<String,Object> getInfo() throws InterruptedException {
        Thread.sleep(3000);
        return studentService.getInfo();
    }
}
