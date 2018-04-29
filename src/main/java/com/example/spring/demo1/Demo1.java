package com.example.spring.demo1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @Author:陈浩杰
 * @description: sad
 * @Date:Created in 20:48 2018/4/29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext1.xml")
@Controller
public class Demo1 {
    @Autowired
    private AccountService accountService;
    @Test
    public void test1(){
        accountService.transfer("小杰","小花",200d);
    }

}
