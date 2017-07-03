package com.ksea.hibernate.transaction;

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

    @Test
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

}
