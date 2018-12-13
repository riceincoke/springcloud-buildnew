package commoncore.feignService;

import commoncore.serviceApi.StudentServiceApi;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author 一杯咖啡
 * @desc feign 客户端  value,name 指定服务提供者的application.name
 * @createTime 2018-12-01-22:48
 */
@FeignClient(name = "provider-student-hystrix", fallbackFactory = StudentFallBackFactory.class)
//@FeignClient(name = "student-provider", fallbackFactory = StudentFallBackFactory.class)
public interface StudentClientService extends StudentServiceApi {
}
