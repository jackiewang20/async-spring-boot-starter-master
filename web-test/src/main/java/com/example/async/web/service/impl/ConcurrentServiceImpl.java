package com.example.async.web.service.impl;

import com.example.async.web.service.ConcurrentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author jackie wang
 * @Title: ConcurrentServiceImpl
 * @ProjectName async-invoke-starter-master
 * @Description: 并发服务:实现同步，异步处理任务
 * @date 2019/11/21 15:43
 */
@Slf4j
@Service
public class ConcurrentServiceImpl implements ConcurrentService {

    @Override
    public void syncTask() throws Exception {
        log.info("[同步]开始执行同步任务syncTask()。");
        Thread.sleep(3000);
        log.info("[同步]执行同步任务syncTask()完成。");
    }

    /**
     * 异步执行任务
     */
//    @Async("threadPoolExecutor")// ，指定自定义线程池名称
    @Async // 默认使用线程池SimpleAsyncTaskExecutor（每接收一个请求创建一个任务，性能低效），已经通过自定义线程池ThreadPoolExecutor实现。
    @Override
    public void asyncTask() throws Exception {
        log.info("[异步-1]开始执行异步任务asyncTask()。");
        Thread.sleep(3000);
        log.info("[异步-1]执行异步任务asyncTask()完成。");
    }


    /**
     * 异步任务，并返回结果
     *
     * @throws Exception
     */
    @Async
    @Override
    public Future<String> asyncFuture() throws Exception {
        log.info("[异步]---->开始执行异步任务asyncFuture()。");
        Thread.sleep(3000);
        log.info("[异步]---->当前执行任务线程名称：{}", Thread.currentThread().getName());
        log.info("[异步]---->执行异步任务asyncFuture()完成。");
        return new AsyncResult<>("---->异步方法返回的结果");
    }

    /* ================ 制作披萨 ============== */

    /**
     * 购买厨具
     *
     * @throws Exception
     */
    @Async
    @Override
    public Integer buyKitchenware() throws Exception {
        log.info("[异步-1]开始购买厨具。");
        Thread.sleep(3000);
        log.info("[异步-1]购买厨具完成。");
//        log.info("[异步-1]将任务完成状态放入EventBus中。");
        return 0;
    }

    /**
     * 购买食材
     *
     * @return
     * @throws Exception
     */
    @Async
    @Override
    public Integer buyFoodMaterial() throws Exception {
        log.info("[异步-2]开始购买食材。");
        Thread.sleep(3000);
        log.info("[异步-2]购买食材完成。");
//        log.info("[异步-2]将任务完成状态放入EventBus中。");
        return 0;
    }

    /**
     * 烹饪
     *
     * @return
     * @throws Exception
     */
    @Async
    @Override
    public Future<Boolean> cooking() throws Exception {
//        log.info("[异步-2]获取EventBus中的任务，判断购买厨具，食材是否完成。");
        // todo something --> getEventBus();
        log.info("[异步-3]开始烹饪准备工作，备用厨具、食材等...");
        log.info("[异步-3]开始烹饪。");
        Thread.sleep(3000);
        log.info("[异步-3]烹饪完成。");
        return new AsyncResult<>(true);

    }

    /**
     * 购买厨具
     *
     * @throws Exception
     */
    @Async
    @Override
    public Future<Object> buyKitchenware2() throws Exception {
        log.info("[异步-1]开始购买厨具。");
        Thread.sleep(3000);
        log.info("[异步-1]购买厨具完成。");
        return new AsyncResult<>("购买了一套苏泊尔电饭锅套装。");
    }

    /**
     * 购买食材
     *
     * @return
     * @throws Exception
     */
    @Async
    @Override
    public Future<Object> buyFoodMaterial2() throws Exception {
        log.info("[异步-2]开始购买食材。");
        Thread.sleep(3000);
        log.info("[异步-2]购买食材完成。");
        return new AsyncResult<>("购买了3份食材：面粉、火腿、番茄。");
    }

    /**
     * 烹饪
     *
     * @return
     * @throws Exception
     */
    @Async
    @Override
    public Future<Object> cooking2() throws Exception {
        log.info("[异步-3]开始烹饪。");
        Thread.sleep(3000);
        log.info("[异步-3]烹饪完成。");

        return new AsyncResult<>("披萨烹饪完成。");
    }

}
