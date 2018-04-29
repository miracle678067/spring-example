package com.example.spring.demo2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @Author:陈浩杰
 * @description: sad
 * @Date:Created in 20:08 2018/4/29
 */
@Repository
public class AccountDaoImpl2 implements AccountDao2 {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     *
     * @param out 转出账号
     * @param money 转账金额
     */
    @Override
    public void outMoney(String out, double money) {
        String sql = "update account set money = money - ? where name = ?";
        //update(sql,params,types)
        //params是参数，如果sql语句没有写完整，而是用了占位符？，那么就要带上参数来替代掉占位符
        //types是执行完该sql语句后的返回类型
        jdbcTemplate.update(sql,money,out);
    }

    @Override
    public void inMoney(String in, double money) {
        String sql = "update account set money = money + ? where name = ?";
        jdbcTemplate.update(sql,money,in);
    }


}
