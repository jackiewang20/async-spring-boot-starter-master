package com.example.async.starter.configuration;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;

import java.util.concurrent.*;

/**
 * @author jackie wang
 * @Title: AsyncAutoConfiguration
 * @ProjectName async-invoke-starter-master
 * @Description: 参考@EnableAsync注解注释说明，实现AsyncConfigurerSupport类，通过自定义线程池ThreadPoolExecutor
 * 替换默认的线程池。
 * @date 2019/11/21 14:29
 */
@Configuration
@EnableConfigurationProperties(AsyncProperties.class) // 激活属性配置
public class AsyncAutoConfiguration extends AsyncConfigurerSupport {
    @Autowired
    private AsyncProperties asyncProperties;

    @Override
    public Executor getAsyncExecutor() {
        return threadPoolExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return super.getAsyncUncaughtExceptionHandler();
    }

    /**
     * 自定义线程池threadPoolExecutor
     * @return
     */
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(asyncProperties.getCorePoolSize(), asyncProperties.getMaximumPoolSize(),
                asyncProperties.getKeepAliveTime(), TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(asyncProperties.getQueueCapacity()),
                new NamedThreadFactory(asyncProperties.getThreadName()));
        return threadPoolExecutor;
    }

}
