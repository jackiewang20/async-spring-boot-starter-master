package com.example.async.web.service;

import org.springframework.scheduling.annotation.AsyncResult;

import java.util.concurrent.Future;

/**
 * @author jackie wang
 * @Title: ConcurrentService
 * @ProjectName async-invoke-starter-master
 * @Description: 并发服务:实现同步，异步处理任务
 * @date 2019/11/21 15:40
 */
public interface ConcurrentService {

    void syncTask() throws Exception;

    void asyncTask() throws Exception;

    /**
     * 异步任务，并返回结果
     * @throws Exception
     */
    Future<String> asyncFuture() throws Exception;

    /* ================ 制作披萨 ============== */
    /**
     * 购买厨具
     * @throws Exception
     */
    Integer buyKitchenware() throws Exception;

    /**
     * 购买食材
     * @return
     * @throws Exception
     */
    Integer buyFoodMaterial() throws Exception;

    /**
     * 烹饪
     * @return
     * @throws Exception
     */
    Future<Boolean> cooking() throws Exception;

    /**
     * 购买厨具
     * @throws Exception
     */
    Future<Object> buyKitchenware2() throws Exception;

    /**
     * 购买食材
     * @return
     * @throws Exception
     */
    Future<Object> buyFoodMaterial2() throws Exception;

    /**
     * 烹饪
     * @return
     * @throws Exception
     */
    Future<Object> cooking2() throws Exception;

}
