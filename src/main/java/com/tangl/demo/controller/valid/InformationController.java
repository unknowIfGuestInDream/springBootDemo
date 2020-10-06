package com.tangl.demo.controller.valid;

import com.tangl.demo.Document.valid.Information;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author: TangLiang
 * @date: 2020/9/2 8:58
 * @since: 1.0
 */
@Api(tags = "信息校验")
@RestController
@Validated
public class InformationController {

    @ApiOperation("传入信息")
    @PostMapping("/infor")
    public String testDemo(@Valid Information demo, BindingResult bindingResult) {
        StringBuffer stringBuffer = new StringBuffer();
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            for (ObjectError objectError : list) {
                stringBuffer.append(objectError.getDefaultMessage());
                stringBuffer.append("---");
            }
        }
        return stringBuffer != null ? stringBuffer.toString() : "";
    }

    @GetMapping("/inforOther")
    public String inforOther(@NotNull(message = "名字不能为空") String name, @Max(value = 99, message = "年龄不能大于99岁") Integer age) {
        return "name: " + name + " ,age:" + age;
    }
}
