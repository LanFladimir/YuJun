package com.fladimir.jutils.ui.rotatingtext;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by NingJiang on 2017/6/5.
 * Class Note:
 */

public class RotatavleUtils {
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1;
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
}