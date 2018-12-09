package consumercore.consumerController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import commoncore.entity.Student;
import commoncore.serviceApi.StudentConsumerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author 一杯咖啡
 * @desc
 * @createTime 2018-12-09-23:20
 */
@RestController
public class StudentHystrixConsumerController implements StudentConsumerApi {

    @Value("${info.provider}")
    private String appName;
    @Value("${appLoaction}")
    private String PRE_HOST;
    @Autowired
    private RestTemplate restTemplate;


    @Override
    public Student findById(int id) {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return restTemplate.getForObject(PRE_HOST + "/provider/find/" + id, Student.class);
    }

    @Override
    public List<Student> findAll() {
        Future<List<Student>> list = this.findAllByAsync();
        try {
            return list.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return null;
        } catch (ExecutionException e) {
            return null;
        } catch (TimeoutException e) {
            return null;
        }
    }
    /**
     * desc: 异步发送请求
     **/
    @HystrixCommand(commandKey = "cacheAll",groupKey = "consumer", threadPoolKey = "findAll",
            fallbackMethod = "fallAll", commandProperties = @HystrixProperty(name="requestCache.enabled",value = "true"))
    @CacheResult
    @CacheRemove(commandKey = "cacheAll")
    public Future<List<Student>> findAllByAsync() {
        return new AsyncResult() {
            @Override
            public Object invoke() {
                return restTemplate.getForObject(PRE_HOST + "/provider/findAll", List.class);
            }

            @Override
            public Object get(long timeout, TimeUnit unit) throws UnsupportedOperationException {
                try {
                    unit.sleep(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return this.invoke();
            }

            @Override
            public Object get() throws UnsupportedOperationException {
                return this.invoke();
            }
        };
    }

    @Override
    public boolean deleteOne(int id) {
        return restTemplate.getForObject(PRE_HOST + "/provider/delete/" + id, Boolean.class);
    }

    @Override
    public boolean insertOne(Student student) {
        student = new Student();
        student.setGrade("一年级");
        student.setName("你是");
        return restTemplate.postForObject(PRE_HOST + "/provider/save/", student, Boolean.class);
    }

    @Override
    public Map getInfo() {
        return restTemplate.getForObject(PRE_HOST + "/provider/getInfo/", Map.class);
    }
}
