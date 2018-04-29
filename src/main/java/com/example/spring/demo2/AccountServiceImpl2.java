package com.example.spring.demo2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;

import javax.annotation.Resource;

/**
 * @Author:陈浩杰
 * @description: sad
 * @Date:Created in 20:05 2018/4/29
 */
@Service
public class AccountServiceImpl2 implements AccountService2 {
    @Autowired
    private AccountDao2 accountDao2;


    /**
     *
     * @param out 转出账户
     * @param in  转入账户
     * @param money 转账金额
     */
    @Override
    public void transfer(final String out, final String in, final double money) {
        accountDao2.outMoney(out,money);
       // int i = 1/0;
        accountDao2.inMoney(in,money);
    }
}
