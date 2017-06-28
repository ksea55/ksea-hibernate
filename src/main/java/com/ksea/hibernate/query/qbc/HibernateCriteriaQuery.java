package com.ksea.hibernate.query.qbc;

import com.ksea.hibernate.bo.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by mexican on 2017/6/27.
 */
public class HibernateCriteriaQuery {
    private Session session;

    @Before
    public void before() {

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate/config/hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources().buildMetadata(serviceRegistry).buildSessionFactory();
        session = sessionFactory.getCurrentSession();

    }

    @Test
    public void test1() {

        Transaction transaction = session.beginTransaction();
        //这里在5.2版本之后，Criteria已经过时，Hibernate更是用JPA的方式来获取
        List list = session.createCriteria(Student.class).list();
        System.out.println(list);
        transaction.commit();
    }
}
