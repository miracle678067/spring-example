package com.example.spring.demo2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;

import javax.annotation.Resource;

/**
 * @Author:陈浩杰
 * @description: sad
 * @Date:Created in 21:27 2018/4/29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext2.xml")
@Controller
public class Demo2 {
    @Resource(name = "accountServiceProxy")
    private AccountService2 accountService2;
    @Test
    public void test2(){
        accountService2.transfer("小杰","小花",200d);
    }
}
