package hystixcore.services;

import java.util.List;
import java.util.Map;

/**
 * @author 一杯咖啡
 * @desc  服务接口
 * @createTime 2018-12-01-15:43
 */
public interface IService<T> {
    T findById(int id);
    List<T> findList();
    boolean insertOne(T o);
    boolean deleteById(int id);
    boolean notityById(int id);
    Map getInfo() throws InterruptedException;
}
