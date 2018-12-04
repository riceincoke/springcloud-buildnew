package hystixcore.services;

import commoncore.entity.Student;
import hystixcore.dao.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 一杯咖啡
 * @desc
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
    public Map<String, Object> getInfo() {
        Map<String,Object> map = new HashMap<>(3);
        map.put("tips:","this is a map tip" );
        map.put("info:","带熔断的student provider 返回该数据" );
        map.put("服务者：", name);
        return map;
    }
}
