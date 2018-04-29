package com.example.spring.demo3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:陈浩杰
 * @description: sad
 * @Date:Created in 20:05 2018/4/29
 */
@Service
public class AccountServiceImpl3 implements AccountService3 {
    @Autowired
    private AccountDao3 accountDao3;


    /**
     *
     * @param out 转出账户
     * @param in  转入账户
     * @param money 转账金额
     */
    @Override
    public void transfer(final String out, final String in, final double money) {
        accountDao3.outMoney(out,money);
        int i = 1/0;
        accountDao3.inMoney(in,money);
    }
}
