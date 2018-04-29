package com.example.spring.demo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @Author:陈浩杰
 * @description: sad
 * @Date:Created in 20:05 2018/4/29
 */
@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private TransactionTemplate transactionTemplate;
    /**
     *
     * @param out 转出账户
     * @param in  转入账户
     * @param money 转账金额
     */
    @Override
    public void transfer(final String out, final String in, final double money) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                accountDao.outMoney(out,money);
                //模拟异常操作
                int i = 1/0;
                accountDao.inMoney(in,money);
            }
        });
    }
}
