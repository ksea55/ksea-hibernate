package com.ksea.hibernate.query.hql;

import com.ksea.hibernate.bo.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by mexican on 2017/6/27.
 * Hibernate HQL查询
 */
public class HibernateHqlQuery {

    private Session session;

    @Before
    public void before() {

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate/config/hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources().buildMetadata(serviceRegistry).buildSessionFactory();
        session = sessionFactory.getCurrentSession();

    }

    @Test //初始化数据
    public void initData() {
        Transaction transaction = session.beginTransaction();
        for (int i = 1; i <= 20; i++) {
            Student student = new Student("name_" + i, 21 + i, 70 + i);
            session.save(student);
        }
        transaction.commit();
    }


    @Test
    public  void  test1(){
        Transaction transaction = session.beginTransaction();
        List list = session.createQuery("from Student").list();
        System.out.println(list);
        transaction.commit();
    }
}
