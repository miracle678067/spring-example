package com.example.spring.demo1;

/**
 * @Author:陈浩杰
 * @description: sad
 * @Date:Created in 20:06 2018/4/29
 */
public interface AccountDao {
    void outMoney(String out, double money);

    void inMoney(String in, double money);
}
