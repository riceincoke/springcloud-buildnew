package consumercore.consumerController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import commoncore.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author 一杯咖啡
 * @desc 服务消费者
 * @createTime 2018-12-01-15:31
 */
@RestController
@RequestMapping("/student")
public class StudentConsumerController {
    @Value("${info.provider}")
    private String appName;
    @Value("${appLoaction}")
    private String PRE_HOST;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/find/{id}")
    @HystrixCommand(groupKey = "consumer", threadPoolKey = "getstudent", fallbackMethod = "fallBack", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")})
    public Student getStudent(@PathVariable(value = "id") int id) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return restTemplate.getForObject(PRE_HOST + "/provider/find/" + id, Student.class);
    }

    /**
     * desc:熔断降级
     **/
    public Student fallBack(@PathVariable(value = "id") int id) {
        Student student = new Student();
        student.setId(id);
        student.setName(appName);
        student.setGrade("降级服务消费者返回该数据");
        return student;
    }

    /**
     * desc: 查询所有
     **/
    @GetMapping(value = "/findAll")
    //@HystrixCommand(commandKey = "find_all", groupKey = "consumer", threadPoolKey = "findAll", fallbackMethod = "fallAll")
    //@CacheResult
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
        /*Observable<List<Student>> my = this.findAllByObservable();
        Iterator<List<Student>> it = my.toBlocking().getIterator();
        Map map = new HashMap(3);
        while (it.hasNext()){
           map.put("ssss----",it.next());
       }
        return map;*/
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

    /**
     * desc: 订阅者模式，可以返回多个结果存入obserable对象中
     **/
    @HystrixCommand(observableExecutionMode = ObservableExecutionMode.LAZY, groupKey = "consumer", threadPoolKey = "findAll", fallbackMethod = "fallAll")
    public Observable<List<Student>> findAllByObservable() {
        return Observable.create(subscriber -> {
            if (!subscriber.isUnsubscribed()) {
                List<Student> list = restTemplate.getForObject(PRE_HOST + "/provider/findAll", List.class);
                List<Student> list1 = restTemplate.getForObject("http://student-provider/provider/findAll", List.class);
                subscriber.onNext(list);
                subscriber.onNext(list1);
                subscriber.onCompleted();
            }
        });
    }

    public List<Student> fallAll() {
        Student student = new Student();
        student.setId(500);
        student.setGrade("出错熔断");
        List<Student> students = new ArrayList<>();
        students.add(student);
        return students;
    }

    @GetMapping(value = "/delete/{id}")
    public boolean deleteOne(@PathVariable(value = "id") int id) {
        return restTemplate.getForObject(PRE_HOST + "/provider/delete/" + id, Boolean.class);
    }

    @GetMapping(value = "/save")
    @HystrixCommand
    //@CacheRemove(cacheKeyMethod = "findAllByAsync",commandKey = )
    public boolean insertOne() {
        Student student;
        student = new Student();
        student.setGrade("一年级");
        student.setName("张三");
        return restTemplate.postForObject(PRE_HOST + "/provider/save/", student, Boolean.class);
    }

    @GetMapping(value = "/getInfo")
    @HystrixCommand(groupKey = "consumer", threadPoolKey = "getInfo", fallbackMethod = "fallInfo", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")})
    public Map<String, Object> getInfo() {
        return restTemplate.getForObject(PRE_HOST + "/provider/getInfo/", Map.class);
    }

    public Map<String, Object> fallInfo() {
        Map<String, Object> map = new HashMap<>(3);
        map.put("tips:", "this is a map tip");
        map.put("info:", "student consumer 返回该数据");
        map.put("被调用者：", appName);
        return map;
    }
}
