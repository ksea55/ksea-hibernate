package com.ksea.hibernate.test;

import com.ksea.hibernate.bo.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;

/**
 * Created by mexican on 2017/6/27.
 */
public class HibernateTest {
    @Test
    public void test() {
        //创建文件注册服务
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate/config/hibernate.cfg.xml").build();
        //获取sessionFactor工厂
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();

        //获取session
        Session session = sessionFactory.getCurrentSession();

        //开启事务
        Transaction transaction = session.beginTransaction();
        try {
            Student student = new Student("jacky", 21, 100);
            session.save(student);
            transaction.commit();
        } catch (Exception e) {

            e.printStackTrace();
            transaction.rollback();
        }


    }
}
