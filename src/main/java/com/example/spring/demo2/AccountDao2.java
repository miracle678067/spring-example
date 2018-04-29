package com.example.spring.demo2;

/**
 * @Author:陈浩杰
 * @description: sad
 * @Date:Created in 20:06 2018/4/29
 */
public interface AccountDao2 {
    void outMoney(String out, double money);

    void inMoney(String in, double money);
}
