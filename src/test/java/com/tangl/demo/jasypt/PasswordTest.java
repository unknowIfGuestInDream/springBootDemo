package com.tangl.demo.jasypt;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * jasypt加密密码
 *
 * @author: TangLiang
 * @date: 2020/12/19 12:14
 * @since: 1.0
 */
@SpringBootTest
public class PasswordTest {
    @Autowired
    private StringEncryptor stringEncryptor;

    /**
     * 生成加密密码
     */
    @Test
    public void testGeneratePassword() {
        // 你的邮箱密码
        String password = "ELACZHTZLMLVJYDF";
        // 加密后的密码(注意：配置上去的时候需要加 ENC(加密密码))
        String encryptPassword = stringEncryptor.encrypt(password);
        String decryptPassword = stringEncryptor.decrypt(encryptPassword);

        System.out.println("password = " + password);
        System.out.println("encryptPassword = " + encryptPassword);
        System.out.println("decryptPassword = " + decryptPassword);
    }
}
