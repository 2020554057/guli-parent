package com.atguigu.staservice.utils;

import java.util.Random;

public class RandomUtils {
    private static final Random random = new Random();

    public static int nextInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return random.nextInt(max - min + 1) + min;
    }
}
