package com.junlaninfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by 辉 on 2020/3/17.
 */

@SpringBootApplication
public class AutoannotationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AutoannotationApplication.class, args);
    }
}
