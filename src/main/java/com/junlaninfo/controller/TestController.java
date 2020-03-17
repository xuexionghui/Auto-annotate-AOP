package com.junlaninfo.controller;

import com.junlaninfo.AnnoConfig.AutoAnnotationConfig;
import com.junlaninfo.annotation.LogAutoannotation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 辉 on 2020/3/17.
 */
@RestController
public class TestController {
    private Log log = LogFactory.getLog(TestController.class);

    @LogAutoannotation(desc = "测试自定义注解的")
    @GetMapping("test")
    public  String  test(String  name){
        return   "我是"+name;
    }
}
