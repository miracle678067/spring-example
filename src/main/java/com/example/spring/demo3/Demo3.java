package com.example.spring.demo3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @Author:陈浩杰
 * @description: sad
 * @Date:Created in 21:27 2018/4/29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext3.xml")
@Controller
public class Demo3 {
  @Autowired
  private AccountService3 accountService3;
    @Test
    public void test3(){
      accountService3.transfer("小杰","小花",200d);
    }
}
