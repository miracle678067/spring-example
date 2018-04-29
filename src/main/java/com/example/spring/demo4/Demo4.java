package com.example.spring.demo4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author:陈浩杰
 * @description: sad
 * @Date:Created in 21:27 2018/4/29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext4.xml")
@Controller
public class Demo4 {
  @Autowired
  private AccountService4 accountService3;
    @Test
    public void test4(){
      accountService3.transfer("小杰","小花",200d);
    }
}
