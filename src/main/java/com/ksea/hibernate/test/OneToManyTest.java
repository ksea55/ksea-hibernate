package com.ksea.hibernate.test;

import com.ksea.hibernate.relevance.Country;
import com.ksea.hibernate.relevance.Minister;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ksea on 2017/6/28.
 * Hibernate关联关系，一对多单向关联关系
 */
public class OneToManyTest {
    private Session session;

    @Before
    public void before() {
        //创建文件注册服务
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate/config/hibernate.cfg.xml").build();
        //获取sessionFactor工厂
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();

        //获取session
        session = sessionFactory.getCurrentSession();

    }

    @Test
    public void test1() {

        Transaction transaction = session.beginTransaction();
        try {
            Minister m1 = new Minister("国防部长");
            Minister m2 = new Minister("外交部长");
            Country c1 = new Country("俄罗斯");
            c1.getMinisters().add(m1);
            c1.getMinisters().add(m2);
         /*
            这种方式虽然能实现，但不常用
            session.save(m2);
            session.save(m1);
            session.save(c1);*/


            /**一般使用这种情况
             * 直接由一方去维护，但这种情况，我们需要在xml中的set配置中配置级联属性
             *   <set name="ministers" cascade="save-update">
             * */
            session.save(c1);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }


    }
}
