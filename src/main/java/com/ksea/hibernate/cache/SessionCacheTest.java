package com.ksea.hibernate.cache;

import com.ksea.hibernate.bo.Student;
import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ksea on 2017/6/30.
 * Hibernate Session 一级缓存测试
 */
public class SessionCacheTest {

    private Session session;

    @Before
    public void before() {

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate/config/hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        session = sessionFactory.getCurrentSession();
    }

    @Test
    public void sessionCacheTest1() {

        Transaction transaction = session.beginTransaction();
        //查询Id为2的学生信息：第一次查询
        Student student1 = session.get(Student.class, 2);
        System.out.println(student1);

        //查询Id为2的学生信息：第二次查询
        Student student2 = session.get(Student.class, 2);
        System.out.println(student2);

        /***
         *
         *
         * select
         student0_.id as id1_2_0_,
         student0_.s_name as s_name2_2_0_,
         student0_.s_age as s_age3_2_0_,
         student0_.s_score as s_score4_2_0_
         from
         k_student student0_
         where
         student0_.id=?
         Student{id=2, name='name_2', age=23, score=72}
         Student{id=2, name='name_2', age=23, score=72}
         *
         * 从结果我们可以看出，在查询student1的时候是发出了一条sql语句，在查询student2的时候，并没有发出sql语句，就直接从session缓存过来
         */

        try {
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }

    }


    @Test
    public void sessionCacheTest2() {

        Transaction transaction = session.beginTransaction();
        //查询Id为2的学生信息：第一次查询
        Student student1 = session.get(Student.class, 2);
        System.out.println(student1);

        boolean contains1 = session.contains(student1);
        System.out.println("session中是否有student1的缓存:"+contains1);
        session.clear();//情况session缓存


        boolean contains2 = session.contains(student1);
        System.out.println("session中是否有student1的缓存:"+contains2);
        //查询Id为2的学生信息：第二次查询
        Student student2 = session.get(Student.class, 2);
        System.out.println(student2);


/***
 *Hibernate:
 select
 student0_.id as id1_2_0_,
 student0_.s_name as s_name2_2_0_,
 student0_.s_age as s_age3_2_0_,
 student0_.s_score as s_score4_2_0_
 from
 k_student student0_
 where
 student0_.id=?
 Student{id=2, name='name_2', age=23, score=72} 第一次查询 student1发出sql语句
 session中是否有student1的缓存:true //此刻session中是有student1的缓存

 执行session.clear() 清空session缓存

 session中是否有student1的缓存:false //此刻session没有student1对象了

 Hibernate:
 select
 student0_.id as id1_2_0_,
 student0_.s_name as s_name2_2_0_,
 student0_.s_age as s_age3_2_0_,
 student0_.s_score as s_score4_2_0_
 from
 k_student student0_
 where
 student0_.id=?
 Student{id=2, name='name_2', age=23, score=72} //再次查询id为2 的student信息，有发出了sql语句，此刻可以证明上面的案例确实是从session取出的缓存
 *
 *
 *
 *
 *
 */
        try {
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }

    }
}
