package com.wangchg.study.javassist.aop;

/**
 * @author wangchenguang
 * @version 1.0
 * @date 2018/10/10
 */
public class Looper {
    public void loop() {
        System.out.println("Looper.loop invoked");
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
