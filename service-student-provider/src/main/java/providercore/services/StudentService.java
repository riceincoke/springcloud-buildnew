package providercore.services;

import commoncore.entity.Student;
import org.springframework.beans.factory.annotation.Value;
import providercore.dao.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author 一杯咖啡
 * @desc 服务
 * @createTime 2018-11-29-23:49
 */
@Service
public class StudentService implements IService<Student> {
    @Value("${info.provider}") private String name;
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student findById(int id) {
        return studentRepository.findOne(id);
    }

    @Override
    public List<Student> findList() {
        return studentRepository.findAll();
    }

    @Override
    public boolean insertOne(Student o) {
        Student student = studentRepository.save(o);
        return (student == null?false:true);
    }

    @Override
    public boolean deleteById(int id) {
        studentRepository.delete(id);
        return true;
    }

    @Override
    public boolean notityById(int id) {
        return false;
    }

    @Override
    public Map getInfo() {
        Map<String,Object> map = new HashMap<>(4);
        map.put("tips:","调用了普通服务提供者" );
        map.put("作用", "模拟超时3秒内随机时间返回数据");
        map.put("info:","【student-provider】 返回该数据" );
        map.put("被调用者：",name);
        return map;
    }
}
