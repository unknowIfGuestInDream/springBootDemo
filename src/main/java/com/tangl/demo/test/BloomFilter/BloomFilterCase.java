package com.tangl.demo.test.BloomFilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @author: TangLiang
 * @date: 2020/10/11 13:54
 * @since: 1.0
 */
public class BloomFilterCase {
    /*
    预计插入多少数据
     */
    private static int size = 1000000;
    /*
    误判率
     */
    private static double fpp = 0.01;

    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, fpp);

    public static void main(String[] args) {
        for (int i = 0; i < size; i++) {
            bloomFilter.put(i);
        }

        int count = 0;
        for (int i = size; i < size + 100000; i++) {
            if (bloomFilter.mightContain(i)) {
                count++;
            }
        }
        System.out.println("总共误判数量: " + count);

    }

}
