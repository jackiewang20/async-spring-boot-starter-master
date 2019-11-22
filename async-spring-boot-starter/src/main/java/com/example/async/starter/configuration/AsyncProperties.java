package com.example.async.starter.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

import static com.example.async.starter.configuration.AsyncProperties.PREFIX;

/**
 * @author jackie wang
 * @Title: AsyncProperties
 * @ProjectName async-invoke-starter-master
 * @Description: TODO
 * @date 2019/11/21 14:29
 */
@Data
@ConfigurationProperties(PREFIX)
public class AsyncProperties {
    public static final String PREFIX = "spring.async";
    /** Minimum number of threads(最小线程数) */
    private Integer corePoolSize=3;
    /** Maximum number of threads(最大线程数) */
    private Integer maximumPoolSize=9;
    /** Idle thread collection interval in seconds(空闲线程回收间隔时间，单位为秒) */
    private Integer keepAliveTime=30;
    /** Thread pool queue capacity(线程池队列容量) */
    private Integer queueCapacity = 2000;
    /** Thread custom name: character length is now 6 characters(线程自定义名称：字符长度现在6个字符) */
    private String threadName ="异步任务";


}
