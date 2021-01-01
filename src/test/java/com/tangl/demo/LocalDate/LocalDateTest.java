package com.tangl.demo.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * java8日期API
 *
 * @author: TangLiang
 * @date: 2021/1/1 22:38
 * @since: 1.0
 */
@SpringBootTest
public class LocalDateTest {
    /**
     * 设置格式化模板
     **/
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSS");

    /**
     * 设置日期时区常量
     **/
    public static final ZoneId CHINA_ZONE_ID = ZoneId.systemDefault();

    /**
     * Date格式化为DateTime
     **/
    @Test
    public void dateToDateTime() {
        Date date = new Date();
        LocalDateTime dateTime = date.toInstant().atZone(CHINA_ZONE_ID).toLocalDateTime();
        System.out.println(dateTime);
    }

    /**
     * LocalDate/LocalDateTime转Date
     **/
    @Test
    public void toDate() {
        // LocalDate
        LocalDate localDate = LocalDate.now();
        Date d1 = Date.from(localDate.atStartOfDay(CHINA_ZONE_ID).toInstant());
        System.out.println(d1);

        // LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.now();
        Date d2 = Date.from(localDateTime.atZone(CHINA_ZONE_ID).toInstant());
        System.out.println(d2);
    }

    /**
     * 日期格式化
     **/
    @Test
    public void formatDate() {
        System.out.println(LocalDateTime.now().format(DATE_TIME_FORMATTER));
    }

    /**
     * 日期加减
     **/
    @Test
    public void plusDay() {
        LocalDateTime dateTime = LocalDateTime.now(CHINA_ZONE_ID);
        //天
        dateTime = dateTime.plusDays(1);
        //时
        dateTime = dateTime.plusHours(-1);
        //分钟
        dateTime = dateTime.plusMinutes(30);
        System.out.println(dateTime.format(DATE_TIME_FORMATTER));
    }

    /**
     * 日期时间间隔
     **/
    @Test
    public void betweenDay() {
        // LocalDateTime
        LocalDateTime startDate = LocalDateTime.of(2019, 07, 01, 12, 12, 22);
        LocalDateTime endDate = LocalDateTime.of(2019, 07, 03, 12, 12, 22);
        Long withSecond = endDate.atZone(CHINA_ZONE_ID).toEpochSecond() - startDate.atZone(CHINA_ZONE_ID).toEpochSecond();
        System.out.println(withSecond / 60 / 60 / 24);

        // LocalDate
        LocalDate startDate2 = LocalDate.of(2019, 07, 01);
        LocalDate endDate2 = LocalDate.of(2019, 07, 03);
        Long withSecond2 = endDate2.toEpochDay() - startDate2.toEpochDay();
        System.out.println(withSecond2);
    }

    /**
     * 第一天and最后一天
     **/
    @Test
    public void theLastDay() {
        // 当月第一天
        LocalDateTime dateTime = LocalDateTime.of(2019, 07, 03, 12, 12, 22);
        dateTime = dateTime.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println(dateTime);
        // 当月最后一天
        dateTime = dateTime.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(dateTime);

        //当月的第几天
        dateTime = LocalDateTime.now();
        int dayOfMonth = dateTime.getDayOfMonth();
        System.out.println(dayOfMonth);
        // 当前周的第几天
        int dayOfWeek = dateTime.getDayOfWeek().getValue();
        System.out.println(dayOfWeek);
    }

}
