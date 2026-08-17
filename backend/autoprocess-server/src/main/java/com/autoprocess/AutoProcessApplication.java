package com.autoprocess;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 后端启动入口，负责扫描所有 autoprocess 模块中的 Spring 组件。
 */
@MapperScan("com.autoprocess.mapper")
@SpringBootApplication
public class AutoProcessApplication {
    public static void main(String[] args) {
        SpringApplication.run(AutoProcessApplication.class, args);
    }
}
