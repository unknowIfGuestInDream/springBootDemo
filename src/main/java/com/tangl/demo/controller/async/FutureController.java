package com.tangl.demo.controller.async;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author: TangLiang
 * @date: 2020/10/5 13:07
 * @since: 1.0
 */
@RestController
@Api(tags = "异步调用")
@RequestMapping("/v3")
public class FutureController {
    @Autowired
    private FutureService futureService;

    @PostMapping("/test")
    @ApiOperation(value = "异步测试")
    public String isDoneTest() throws InterruptedException, ExecutionException {
        System.out.println("开始访问");
        long l1 = System.currentTimeMillis();
        Future<String> r1 = futureService.JobOne();
        Future<String> r2 = futureService.JobTwo();
        Future<String> r3 = futureService.JobThree();
//        while (true) {//死循环，每隔2000ms执行一次，判断一下这三个异步调用的方法是否全都执行完了。
//            if (r1.isDone() && r2.isDone() && r3.isDone()) {//使用Future的isDone()方法返回该方法是否执行完成
//                //如果异步方法全部执行完，跳出循环
//                break;
//            }
//            Thread.sleep(2000);//每隔2000毫秒判断一次
//        }
        long l2 = System.currentTimeMillis();//跳出while循环时说明此时三个异步调用的方法都执行完成了，此时得到当前时间

        String result = r1.get();
        System.out.println("结束访问,用时" + (l2 - l1));
        System.out.println("使用get方法获得的返回内容:" + result);
        return r2.get();
    }

}
