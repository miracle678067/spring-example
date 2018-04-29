package com.example.spring.demo4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author:陈浩杰
 * @description: sad
 * @Date:Created in 20:05 2018/4/29
 */
@Service
@Transactional
public class AccountServiceImpl4 implements AccountService4 {
    @Autowired
    private AccountDao4 accountDao4;


    /**
     *
     * @param out 转出账户
     * @param in  转入账户
     * @param money 转账金额
     */
    @Override
    public void transfer(final String out, final String in, final double money) {
        accountDao4.outMoney(out,money);
        int i = 1/0;
        accountDao4.inMoney(in,money);
    }
}
