package commoncore.serviceApi;

import commoncore.entity.Student;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 一杯咖啡
 * @desc
 * @createTime 2018-12-09-23:09
 */
//@RequestMapping(value = "consumer")
public interface StudentConsumerApi {
    @GetMapping(value = "/find/{id}")
    @ResponseBody
    Student providerFindById(@PathVariable(value = "id") int id);

    @GetMapping(value = "/findAll")
    @ResponseBody
    List<Student> providerFindAll();

    @GetMapping(value = "/delete/{id}")
    @ResponseBody
    boolean providerDeleteOne(@PathVariable(value = "id") int id);

    @GetMapping(value = "/save")
    @ResponseBody
    boolean providerInsertOne();

    @GetMapping(value = "/getInfo")
    @ResponseBody
    Map getProviderInfo();
}
