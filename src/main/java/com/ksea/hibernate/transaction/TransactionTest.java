package com.ksea.hibernate.transaction;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ksea on 2017/7/3.
 */
public class TransactionTest {
    private Session session;

    @Before
    public void before() {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate/config/hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        session = sessionFactory.getCurrentSession();
    }

    @Test //Hibernate 中的乐观锁
    public void test1() {
        Transaction transaction = session.beginTransaction();
        try {
            UserInfo userInfo = new UserInfo("jacky", "jacky@qq.com");
            session.save(userInfo);//这里我们并没有添加uversion，但是数据库中就有值了，版本号是有系统进行维护的
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    @Test //hibernate中的悲观锁
    public void test2() {

        Transaction transaction = session.beginTransaction();

        try {
           // Person p=new Person("mexican",12);
           // session.save(p);


            //Hibernate中的悲观锁中的写锁实现
         //   session.get(Person.class,1, LockMode.PESSIMISTIC_WRITE);
            /**
             *
             * 执行的SQL语句：我们可以看到是加了for update的 写锁：排它锁
             * select
             person0_.pid as pid1_5_0_,
             person0_.name as name2_5_0_,
             person0_.age as age3_5_0_
             from
             Person person0_
             where
             person0_.pid=? for update
             */


            //Hibernate中的悲观锁中的读锁：共享锁
            session.get(Person.class,1,LockMode.PESSIMISTIC_WRITE.PESSIMISTIC_READ);
            /**
             * 我们可以看到 SQL语句中是有： lock in share mode  总结悲观锁的锁是加载数据库中的
             * select
             person0_.pid as pid1_5_0_,
             person0_.name as name2_5_0_,
             person0_.age as age3_5_0_
             from
             Person person0_
             where
             person0_.pid=? lock in share mode
             *
             */


            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

    }

}
