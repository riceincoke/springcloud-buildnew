package commoncore.feignService;

import commoncore.entity.Student;
import feign.hystrix.FallbackFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 一杯咖啡
 * @desc
 * @createTime 2018-12-09-23:14
 */
public class ConsumerFallBackFactory implements FallbackFactory<StudentConsumerClient> {
    @Override
    public StudentConsumerClient create(Throwable cause) {
        return new StudentConsumerClient() {
            @Override
            public Student findById(int id) {
                System.out.println("consumer---findByid服务降级被调用");
                Student student = new Student();
                student.setId(id);
                student.setGrade("consumer---降级服务返回该数据");
                return student;
            }

            @Override
            public List<Student> findAll() {
                System.out.println("consumer---findAll服务降级被调用");
                List<Student> mylist = new ArrayList<>();
                Student student = new Student();
                student.setId(500);
                student.setName("consumer---查询所有超时");
                student.setGrade("consumer---熔断服务提供者 降级方法");
                mylist.add(student);
                return mylist;
            }

            @Override
            public boolean deleteOne(int id) {
                System.out.println("consumer---delete服务降级被调用");
                return false;
            }

            @Override
            public boolean insertOne(Student student) {
                System.out.println("consumer---save服务降级被调用");
                return false;
            }

            @Override
            public Map<String, Object> getInfo() {
                System.out.println("consumer---factory,getInfo服务降级被调用");
                Map<String, Object> map = new HashMap<>(2);
                map.put("code", 500);
                map.put("info", "consumer---系统延迟，稍后重试");
                return map;
            }
        };
    }
}
