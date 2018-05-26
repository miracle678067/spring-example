# 通过转账情景学习spring事务管理

​																			——陈浩杰

​																			2018.4.29

**运行环境：**IntelliJ IDEA 2018.1.1

**功能：**通过模拟转账情景运行spring事务管理，分别用了四种方式实现

- 编程式事务管理
- 使用XML配置声明式事务
  1. 基于TransactionProxyFactoryBean的方式
  2. 基于AspectJ的配置方式
- 基于注解的方式

**准备工作**：

- 新建数据库表

  ```
  create table `account`(
  	`id` INT(11) NOT NULL AUTO_INCREMENT,
  	`name` VARCHAR(20) NOT NULL,
  	`money` DOUBLE DEFAULT NULL,
  	PRIMARY KEY(`id`)
  );
  insert into `account` values(1,'小杰','1000');
  insert into `account` values(2,'小花','1000');
  insert into `account` values(3,'小金','1000');
  ```

- 引入响应的jar包（这里使用maven）：

```
<dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
    </dependency>
    <!--数据库连接池-->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.32</version>
    </dependency>
    <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>druid</artifactId>
      <version>1.1.9</version>
    </dependency>
    <!--spring相关的包-->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>5.0.3.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-tx</artifactId>
      <version>5.0.3.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-test</artifactId>
      <version>5.0.3.RELEASE</version>
    </dependency>
    <!--aop相关的包-->
    <dependency>
      <groupId>aopalliance</groupId>
      <artifactId>aopalliance</artifactId>
      <version>1.0</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-aop</artifactId>
      <version>5.0.3.RELEASE</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>5.0.3.RELEASE</version>
    </dependency>
    <!--AspectJ相关的包-->
    <dependency>
      <groupId>org.aspectj</groupId>
      <artifactId>aspectjweaver</artifactId>
      <version>1.8.12</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-aspects</artifactId>
      <version>5.0.3.RELEASE</version>
    </dependency>
  </dependencies>
```

- 配置applicationContext文件：

  ```
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:p="http://www.springframework.org/schema/p"
         xmlns:aop="http://www.springframework.org/schema/aop"
         xmlns:tx="http://www.springframework.org/schema/tx"
         xmlns:context="http://www.springframework.org/schema/context"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
         http://www.springframework.org/schema/beans/spring-beans.xsd
         http://www.springframework.org/schema/aop
         http://www.springframework.org/schema/aop/spring-aop.xsd
         http://www.springframework.org/schema/tx
         http://www.springframework.org/schema/tx/spring-tx.xsd
         http://www.springframework.org/schema/context
         http://www.springframework.org/schema/context/spring-context.xsd">
      <!--引入jdbc属性文件-->
      <context:property-placeholder location="jdbc.properties"/>
      <!--扫描所有的注解bean-->
      <context:component-scan base-package="com.example.spring.demo1"/>
      <!--配置数据源-->
      <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
          <property name="driverClassName" value="${jdbc.driverClass}"/>
          <property name="url" value="${jdbc.url}"/>
          <property name="username" value="${jdbc.username}"/>
          <property name="password" value="${jdbc.password}"/>
      </bean>
      <!--配置jdbc模板-->
      <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
          <constructor-arg ref="dataSource"/>
      </bean>

  </beans>
  ```

事务管理分为***编程式事务管理***和***使用xml配置声明式事务***，下面一一通过代码实现

## 1.编程式事务管理

编程式事务管理需要依赖于事务管理模板（org.springframework.transaction.support.TransactionTemplate）进行事务管理，因此：

- 首先在applicationContext.xml中配置事务管理器和事务管理模板

```
 <!--配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!--配置事务管理模板-->
    <bean id="transactionTemplate" class="org.springframework.transaction.support.TransactionTemplate">
        <property name="transactionManager" ref="transactionManager"/>
    </bean>
```

- 因为事务管理是在业务层进行操作的，因此在业务层（AccountServiceImpl）注入事务管理模板

  ```
  @Autowired
      private TransactionTemplate transactionTemplate;
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
  ```

## 2.使用XML配置声明式事务（基于aop思想）

### 2.1基于TransactionProxyFactoryBean的方式(每次只能对一个目标进行增强，不常使用)（参见demo2）

- 配置业务层代理，对accountServiceImpl进行增强，配置如下

  ```
  <!--配置业务层的代理，对service层进行增强-->
      <bean id="accountServiceProxy" class="org.springframework.transaction.interceptor.TransactionProxyFactoryBean">
          <!--配置代理目标-->
          <property name="target" ref="accountServiceImpl2"/>
          <!--注入事务管理器-->
          <property name="transactionManager" ref="transactionManager2"/>
          <!--注入事务属性-->
          <property name="transactionAttributes">
              <props>
                  <!--PROPAGATION_REQUIRED表示支持当前事务，如果不存在则新建一个-->
                  <prop key="transfer">PROPAGATION_REQUIRED</prop>
              </props>
          </property>
       </bean>
  ```


- 注入代理bean

  ```
  @Resource(name = "accountServiceProxy")
      private AccountService2 accountService2;
  ```

### 2.2基于AspectJ的配置方式（参见demo3）

- 首先要引入aspectjweaver和spring-aspects的jar包

- 在配置文件配置事务、切面、以及切入点

  ```
  <!--配置事务管理器-->
      <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
          <property name="dataSource" ref="dataSource3"/>
      </bean>
      <!--配置事务的通知-->
      <tx:advice id="txAdvice" transaction-manager="transactionManager">
          <tx:attributes>
              <tx:method name="transfer" propagation="REQUIRED"/>
          </tx:attributes>
      </tx:advice>
      <!--配置切面-->
      <aop:config>
          <!--配置切入点-->
          <!--第一个*表示返回值，+表示及其子类，第二个*表示子包，..表示任意参数-->
          <aop:pointcut id="pointcut1" expression="execution(* com.example.spring.demo3.AccountService3+.*(..))"/>
          <!--配置切面-->
          <aop:advisor advice-ref="txAdvice" pointcut-ref="pointcut1"/>
      </aop:config>
  ```

### 2.3基于注解的方式

基于注释使用起来比较简单，只需在配置文件配置事务管理器，同时开启注解事务，在需要使用事务的类上加上@Transactional即可

```
 <!--配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource4"/>
    </bean>
    <!--开启注解事务-->
    <tx:annotation-driven transaction-manager="transactionManager"/>
```

