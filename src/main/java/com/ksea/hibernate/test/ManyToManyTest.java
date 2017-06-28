package com.ksea.hibernate.test;

import com.ksea.hibernate.relevance.Course;
import com.ksea.hibernate.relevance.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by mexican on 2017/6/28.
 * many-to-many多对多关系：它其实有两个one-to-many组成，中间通过一个中间表来进行维护
 */
public class ManyToManyTest {

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

            Teacher t1 = new Teacher("张老师");
            Teacher t2 = new Teacher("王老师");

            Course c1 = new Course("语文");
            Course c2 = new Course("数学");


            t1.getCourses().add(c2);
            t1.getCourses().add(c1);


            t2.getCourses().add(c2);
            t2.getCourses().add(c1);


            session.save(t1);
            session.save(t2);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }

    }
}
