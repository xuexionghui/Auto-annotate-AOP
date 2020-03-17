功能：使用aop实现自定义注解日志记录
1、定义注解 LogAutoannotation
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAutoannotation {

    /*
       接口描述
     */
    String  desc()  default  "";
}
2、配置aop，注解配置 AutoAnnotationConfig
@Aspect
@Component
public class AutoAnnotationConfig {
    private Log log = LogFactory.getLog(AutoAnnotationConfig.class);

    /*
      定义切点
      切到标记注解的方法
     */
    @Pointcut("@annotation(com.junlaninfo.annotation.LogAutoannotation)")
    public void LogAnnotation() {
    }

    @Around("LogAnnotation()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //定义开始的时间
        long begin = System.currentTimeMillis();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        Signature signature = point.getSignature();
        MethodSignature signature1 = (MethodSignature) signature;
        Method method = signature1.getMethod();
        LogAutoannotation annotation = method.getAnnotation(LogAutoannotation.class);
        //接口描述信息
        String desc = annotation.desc();
        log.info("------------请求开始---------");
        log.info("接口描述信息"+desc);
        log.info("请求ip： "+request.getRemoteAddr());
        log.info("请求参数是:"+JSON.toJSONString(point.getArgs()));
        log.info("请求的方法类型："+request.getMethod());
        Object proceed = point.proceed(); //得到方法的返回结果，结果是json的
        long end=System.currentTimeMillis();
        log.info("请求耗时："+(end-begin));
        log.info("请求的返回结果："+ JSON.toJSONString(proceed));
        return proceed;
    }
}

3、使用注解  TestController
@RestController
public class TestController {
    private Log log = LogFactory.getLog(TestController.class);

    @LogAutoannotation(desc = "测试自定义注解的")
    @GetMapping("test")
    public  String  test(String  name){
        return   "我是"+name;
    }
}

4、启动类
@SpringBootApplication
public class AutoannotationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AutoannotationApplication.class, args);
    }
}


注意事项：
AutoAnnotationConfig类上需要加上@Aspect  @Component，不然日志不会生效