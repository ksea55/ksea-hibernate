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
 * Hibernate关联关系，
 */
public class ManyToOneTest {
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

            m1.setCountry(c1);
            m2.setCountry(c1);

            //many-to-one 关联关系
            session.save(m1);
            session.save(m2);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }


    }
}
