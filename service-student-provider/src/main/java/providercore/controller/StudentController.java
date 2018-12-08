package providercore.controller;

import commoncore.entity.Student;
import commoncore.serviceApi.StudentServiceApi;
import providercore.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * @author 一杯咖啡
 * @desc 服务提供者
 * @createTime 2018-11-29-23:48
 */
@RestController
public class StudentController implements StudentServiceApi {
    @Value("${myInfo.name}")
    private String myInfo;
    private Integer counter = 0;

    @Autowired
    private StudentService studentService;

    @Override
    public Student findById(@PathVariable(value = "id") int id) {
        System.out.println ("FINDone---------被调用,"+myInfo+"总共被调用"+(++counter)+"次");
        return studentService.findById(id);
    }

    @Override
    public List<Student> findAll() {
        System.out.println ("FINALL---------被调用,"+myInfo+"总共被调用"+(++counter)+"次");
        return studentService.findList();
    }

    @Override
    public boolean deleteOne(@PathVariable(value = "id") int id) {
        System.out.println ("delete---------被调用,"+myInfo+"总共被调用"+(++counter)+"次");
        return studentService.deleteById(id);
    }

    @Override
    public boolean insertOne(@RequestBody Student student) {
        System.out.println ("save---------被调用,"+myInfo+"总共被调用"+(++counter)+"次");
        return studentService.insertOne(student);
    }

    @Override
    public Map getInfo() {
        System.out.println ("getinfo---------被调用,"+myInfo+"总共被调用"+(++counter)+"次");
        try {
            int sleep = new Random().nextInt(3000);
            TimeUnit.MILLISECONDS.sleep(sleep);
            System.out.println("student-provider sleep time == "+sleep);
        } catch (InterruptedException e) {
            System.out.println("STUDENT-PROVIDER getInfo(),模拟超时出错");
        }
        return studentService.getInfo();
    }
}
