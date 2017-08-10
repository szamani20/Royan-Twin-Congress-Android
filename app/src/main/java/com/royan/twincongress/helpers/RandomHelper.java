package com.royan.twincongress.helpers;

import java.util.Random;

/**
 * Created by szamani on 8/8/2017.
 */

public class RandomHelper {
    public static Random random;

    public static void initRandom() {
        random = new Random(System.currentTimeMillis());
    }
}
