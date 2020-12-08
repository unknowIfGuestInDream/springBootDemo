package com.tangl.demo.jackson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: TangLiang
 * @date: 2020/12/8 13:22
 * @since: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friend {
    private String nickname;
    private int age;
}
