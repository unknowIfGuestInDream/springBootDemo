package com.tangl.demo.controller.erweima;

import com.tangl.demo.util.erweima.QRCodeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 二维码
 *
 * @author: TangLiang
 * @date: 2020/11/28 10:20
 * @since: 1.0
 */
@RestController
@RequestMapping("/qrCode")
public class QRCodeGeneratorController {

    @GetMapping("/generator")
    public void encodeQrCode(String codeContent, HttpServletResponse response) {
        // 嵌入二维码的图片路径
        String imgPath = "C:\\Users\\admin\\Pictures\\Camera Roll\\Q版照片.jpg";
        try {
            //SystemUtil.dumpSystemInfo();
            QRCodeUtil.encode(codeContent, imgPath, true, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
