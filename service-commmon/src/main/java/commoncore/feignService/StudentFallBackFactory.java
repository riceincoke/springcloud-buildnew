package commoncore.feignService;

import commoncore.entity.Student;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 一杯咖啡
 * @desc 服务降级处理
 * @createTime 2018-12-03-13:50
 */
@Component
public class StudentFallBackFactory implements FallbackFactory<StudentClientService> {


    @Override
    public StudentClientService create(Throwable cause) {
        return new StudentClientService() {
            @Override
            public Student findById(int id) {
                System.out.println("findByid服务降级被调用");
                Student student = new Student();
                student.setId(id);
                student.setGrade("feign降级服务返回该数据");
                return student;
            }

            @Override
            public List<Student> findAll() {
                System.out.println("findAll服务降级被调用");
                List<Student> mylist = new ArrayList<>();
                Student student = new Student();
                student.setId(500);
                student.setName("查询所有超时");
                student.setGrade("熔断服务提供者 降级方法");
                mylist.add(student);
                return mylist;
            }

            @Override
            public boolean deleteOne(int id) {
                System.out.println("delete服务降级被调用");
                return false;
            }

            @Override
            public boolean insertOne(Student student) {
                System.out.println("save服务降级被调用");
                return false;
            }

            @Override
            public Map<String, Object> getInfo() {
                System.out.println("factory,getInfo服务降级被调用");
                Map<String, Object> map = new HashMap<>(2);
                map.put("code", 500);
                map.put("info", "系统延迟，稍后重试");
                return map;
            }
        };
    }
}
