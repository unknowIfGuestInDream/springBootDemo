package com.tangl.demo.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;

/**
 * @author: TangLiang
 * @date: 2020/7/2 14:00
 * @since: 1.0
 */
@Aspect
@Component
public class WebLogAspect {
    private static Logger logger = Logger.getLogger(WebLogAspect.class);

    /**
     * 定义切入点，切入点为com.example.demo.aop.AopController中的所有函数
     * 通过@Pointcut注解声明频繁使用的切点表达式
     */
    @Pointcut("execution(public * com.tangl.demo.controller.FirstController.selectTest(..)))")
    public void BrokerAspect() {

    }

    /**
     * @description 在连接点执行之前执行的通知
     */
    @Before("BrokerAspect()")
    public void doBeforeGame(JoinPoint joinPoint) {
        System.out.println("目标方法名为:" + joinPoint.getSignature().getName());
        System.out.println("目标方法所属类的简单类名:" + joinPoint.getSignature().getDeclaringType().getSimpleName());
        System.out.println("目标方法所属类的类名:" + joinPoint.getSignature().getDeclaringTypeName());
        System.out.println("目标方法声明类型:" + Modifier.toString(joinPoint.getSignature().getModifiers()));
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            System.out.println("第" + (i + 1) + "个参数为:" + args[i]);
        }
        System.out.println("被代理的对象:" + joinPoint.getTarget());
        System.out.println("代理对象自己:" + joinPoint.getThis());
    }

    @Around("BrokerAspect()")
    public Object doAround(ProceedingJoinPoint pjd) {
        Object result = null;

        try {
            //前置通知
            System.out.println("目标方法执行前...");
            //执行目标方法
            //result = pjd.proeed();
            //用新的参数值执行目标方法
            result = pjd.proceed(new Object[]{"newSpring", "newAop"});
            //返回通知
            System.out.println("目标方法返回结果后...");
        } catch (Throwable e) {
            //异常通知
            System.out.println("执行目标方法异常后...");
            throw new RuntimeException(e);
        }
        //后置通知
        System.out.println("目标方法执行后...");

        return result;
    }

    /**
     * @description 在连接点执行之后执行的通知（返回通知和异常通知的异常）
     */
    @After("BrokerAspect()")
    public void doAfterGame(JoinPoint joinPoint) {
        System.out.println("doAfterGame");
    }

    /**
     * @description 在连接点执行之后执行的通知（返回通知）
     */
    @AfterReturning(value = "BrokerAspect()",returning = "result")
    public void doAfterReturningGame(JoinPoint joinPoint,Object result) {
        System.out.println(this.getClass().getSimpleName()+" afterReturning execute, result:"+result);
        System.out.println("doAfterReturningGame");
    }

    /**
     * @description 在连接点执行之后执行的通知（异常通知）
     */
    @AfterThrowing(value = "BrokerAspect()",throwing = "exception")
    public void doAfterThrowingGame(JoinPoint joinPoint,Exception exception) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println(this.getClass().getSimpleName()+" afterThrowing execute, exception:"+exception);

        System.out.println("doAfterThrowingGame");
    }
}
