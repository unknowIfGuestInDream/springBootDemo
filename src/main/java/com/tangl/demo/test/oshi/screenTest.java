package com.tangl.demo.test.oshi;

import java.awt.*;

/**
 * 查看电脑屏幕
 *
 * @author: TangLiang
 * @date: 2020/7/22 17:09
 * @since: 1.0
 */
public class screenTest {
    public static void main(String[] args) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();//获得默认得Toolkit
        Dimension screen = toolkit.getScreenSize();//获得显示器大小
        int w = screen.width;
        int h = screen.height;
        int dpi = Toolkit.getDefaultToolkit().getScreenResolution();
        System.out.println(w / dpi * 2.54);
        System.out.println(h / dpi * 2.54);
        System.out.println(dpi);
    }
}
