package com.ksea.hibernate.query.sql;

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
public class HibernateSqlQuery {

    private Session session;

    @Before
    public void before() {

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate/config/hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources().buildMetadata(serviceRegistry).buildSessionFactory();
        session = sessionFactory.getCurrentSession();

    }

    @Test
    public void test1() {
        //Hibernate基于数据库中原生sql查询语句查询数据
        Transaction transaction = session.beginTransaction();

        String sql = "select id,s_name,s_age,s_score from k_student";
        List<Student> list = session.createNativeQuery(sql).addEntity(Student.class).list();
        System.out.println(list);
        transaction.commit();
    }

}
