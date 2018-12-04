package commoncore.hystrix;

import commoncore.entity.Student;
import commoncore.feginService.StudentClientService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

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
            public Student getStudent(int id) {
                return null;
            }

            @Override
            public List<Student> findAll() {
                return null;
            }

            @Override
            public boolean deleteOne(int id) {
                return false;
            }

            @Override
            public boolean insertOne(Student student) {
                return false;
            }

            @Override
            public Map<String, Object> getInfo() {
                Map<String,Object> map = new HashMap<>(2);
                map.put("code", 500);
                map.put("info", "系统延迟，稍后重试");
                return map;
            }
        };
    }
}
