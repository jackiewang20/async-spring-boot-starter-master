package com.example.async.web.controller;

import com.example.async.web.service.ConcurrentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author jackie wang
 * @Title: AsyncController
 * @ProjectName async-invoke-starter-master
 * @Description: 测试同步执行，异步执行的性能测试。
 * @date 2019/11/21 14:07
 */
@Slf4j
@RestController
public class TestController {
    @Autowired
    private ConcurrentService concurrentService;

    /**
     * 同步执行
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "sync/work", method = RequestMethod.GET)
    public String syncWork() throws Exception {
        long startTime = System.currentTimeMillis();
        log.info("[Controller]开始执行任务：当前时间为{}", startTime);
        // 同步调用任务
        concurrentService.syncTask();
        long endTime = System.currentTimeMillis() - startTime;
        log.info("[Controller]执行任务完成，耗时{}", endTime);

        return "success";
    }

    /**
     * 异步执行
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "async/work", method = RequestMethod.GET)
    public String asyncWork() throws Exception {
        long startTime = System.currentTimeMillis();
        log.info("[Controller]开始执行异步任务：当前时间为{}ms", startTime);
        // 异步调用任务
        concurrentService.asyncTask();
        long endTime = System.currentTimeMillis() - startTime;
        log.info("[Controller]执行异步任务完成，耗时{}ms", endTime);

        return "success";
    }

    /**
     * 异步执行，并返回异步回调结果，然后主线程继续执行；
     * 说明：2个异步任务，每个执行的时间是3秒，如果单线程执行，总时间为6秒，由于是使用线程池多线程
     * 异步执行，耗时3秒，加上主线程耗时2秒，总耗时5秒多；如果提交2个异步线程后，再执行主线程任务
     * 那么执行的时间为3秒多。
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "async/result", method = RequestMethod.GET)
    public String asyncResult() throws Exception {
        long startTime = System.currentTimeMillis();
        log.info("[Controller]开始执行任务：当前时间为{}ms", startTime);

        // 异步执行任务1
        concurrentService.asyncTask();
        long endTime1 = System.currentTimeMillis() - startTime;
        log.info("[Controller]异步任务1已提交，耗时{}ms", endTime1);

        // 异步调用任务2，并返回回调结果
        Future<String> stringFuture = concurrentService.asyncFuture();

        // 主线程执行其它任务
        System.out.println("主线程执行其它任务...");
        Thread.sleep(2000);

        String result = null;
        try {
            // get方法或线程阻塞住，直到获取执行任务结果
            result = stringFuture.get(10, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            throw new TimeoutException("concurrentService.asyncFuture()执行任务超时。");
        }

        long asyncEndTime = System.currentTimeMillis() - startTime;
        log.info("[Service asyncFuture()]异步任务耗时{}ms", asyncEndTime);

        long endTime = System.currentTimeMillis() - startTime;
        log.info("[Controller]执行任务完成，耗时{}ms", endTime);

        return result;
    }

    /**
     * 异步执行，并返回异步回调结果，然后主线程继续执行；
     * 说明：2个异步任务，每个执行的时间是3秒，如果单线程执行，总时间为6秒，由于是使用线程池多线程
     * 异步执行，耗时3秒，加上主线程耗时2秒，总耗时5秒多；如果提交2个异步线程后，再执行主线程任务
     * 那么执行的时间为3秒多。
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "async/cooking", method = RequestMethod.GET)
    public Boolean asyncResultCooking() throws Exception {
        long startTime = System.currentTimeMillis();
        log.info("[Controller]开始执行任务：当前时间为{}ms", startTime);

        // 异步执行任务：购买厨具
        concurrentService.buyKitchenware();
        long endTime1 = System.currentTimeMillis() - startTime;
        log.info("[Controller]购买厨具任务已提交，耗时{}ms", endTime1);

        // 异步执行任务：购买食材
        concurrentService.buyFoodMaterial();
        long endTime2 = System.currentTimeMillis() - startTime;
        log.info("[Controller]购买食材任务已提交，耗时{}ms", endTime2);

        // 异步调用任务，烹饪，并返回回调结果
        Future<Boolean> cooking = concurrentService.cooking();

        // 主线程执行其它任务
        System.out.println("主线程执行其它任务...");
        Thread.sleep(2000);

        long cookingStartTime = System.currentTimeMillis();
        Boolean result = null;
        try {
            // get方法或线程阻塞住，直到获取执行任务结果
            result = cooking.get(10, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            throw new TimeoutException("执行烹饪任务超时。");
        }

        long cookingEndTime = System.currentTimeMillis() - cookingStartTime;
        log.info("[Service asyncFuture()]异步任务耗时{}ms", cookingEndTime);

        long endTime = System.currentTimeMillis() - startTime;
        log.info("[Controller]执行任务完成，耗时{}ms", endTime);

        return result;
    }

    /**
     * 异步执行多个任务，并返回每个异步任务回调结果；异步任务提交后，然后主线程继续执行，最终汇总多个异步任务结果；
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "async/multi", method = RequestMethod.GET)
    public String asyncMultiResultCooking() throws Exception {
        // 创建任务集合
//        List<FutureTask<Object>> taskList = new ArrayList<>();
        List<Future<Object>> taskList = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        log.info("[Controller]开始执行任务：当前时间为{}ms", startTime);

        // 异步执行任务：购买厨具
        Future<Object> buyKitchenware2 = concurrentService.buyKitchenware2();
        taskList.add(buyKitchenware2);
        long endTime1 = System.currentTimeMillis() - startTime;
        log.info("[Controller]购买厨具任务已提交，耗时{}ms", endTime1);

        // 异步执行任务：购买食材
        Future<Object> buyFoodMaterial2 = concurrentService.buyFoodMaterial2();
        taskList.add(buyFoodMaterial2);
        long endTime2 = System.currentTimeMillis() - startTime;
        log.info("[Controller]购买食材任务已提交，耗时{}ms", endTime2);

        // 异步调用任务，烹饪，并返回回调结果
        Future<Object> cooking2 = concurrentService.cooking2();
        taskList.add(cooking2);
        long endTime3 = System.currentTimeMillis() - startTime;
        log.info("[Controller]烹饪任务已提交，耗时{}ms", endTime3);

        // 主线程执行其它任务
        System.out.println("主线程执行其它任务...");
        Thread.sleep(2000);

        long cookingStartTime = System.currentTimeMillis();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("制作披萨流程：");

        // 汇总异步执行任务的结果
        for (Future<Object> future : taskList) {
            try {
                // get方法或线程阻塞住，直到获取执行任务结果
                stringBuffer.append(future.get(10, TimeUnit.SECONDS));
            } catch (TimeoutException e) {
                throw new TimeoutException("执行异步任务超时。任务方法名称："+future.toString());
            }
        }

        long cookingEndTime = System.currentTimeMillis() - cookingStartTime;
        log.info("[Service asyncFuture()]异步任务耗时{}ms", cookingEndTime);

        long endTime = System.currentTimeMillis() - startTime;
        log.info("[Controller]执行任务完成，耗时{}ms", endTime);

        return stringBuffer.toString();
    }

}
