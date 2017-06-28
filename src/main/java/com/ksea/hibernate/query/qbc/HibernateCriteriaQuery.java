package com.ksea.hibernate.query.qbc;

import com.ksea.hibernate.bo.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.*;
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

    @Test
    public void test2() {
        //QCB 排序查询
        Transaction transaction = session.beginTransaction();
        List list = session.createCriteria(Student.class).addOrder(Order.desc("age")).list();
        list.forEach(System.out::println);
        transaction.commit();
    }

    @Test
    public void test3() {
        //QCB 动态绑定参数，执行数据查询
        Transaction transaction = session.beginTransaction();
        List list = session.createCriteria(Student.class)
                .add(Restrictions.ge("age", 36)) // age>=36
                .add(Restrictions.le("score", 88)) //score<=88
                .list();
        list.forEach(System.out::println);
        transaction.commit();
    }

    @Test
    public void test4() {
        //QCB 分页查询
        Transaction transaction = session.beginTransaction();
        List list = session.createCriteria(Student.class)
                .setFirstResult((2 - 1) * 5) //startIndex=(startIndex-1)*pageSize
                .setMaxResults(5) //pageSize
                .list();
        list.forEach(System.out::println);
        transaction.commit();
    }

    @Test
    public void test5() {
        //QCB like 模糊查询
        Transaction transaction = session.beginTransaction();
        List list = session.createCriteria(Student.class)
                .add(Restrictions.like("name", "%3%"))
                .list();
        list.forEach(System.out::println);
        transaction.commit();
    }

    @Test
    public void test6() {
        //QCB uniue唯一性查询
        Transaction transaction = session.beginTransaction();
        Student student = (Student) session.createCriteria(Student.class)
                .add(Restrictions.eq("id", 3))
                .uniqueResult();
        System.out.println(student);
        transaction.commit();
    }


    @Test
    public void test7() {
        //QCB 聚合函数查询
        Transaction transaction = session.beginTransaction();
        Long rowCount = (Long) session.createCriteria(Student.class)
                .setProjection(Projections.rowCount())
                .uniqueResult();
        System.out.println(rowCount); //统计总行数 :20


        Long count = (Long) session.createCriteria(Student.class).setProjection(Projections.count("id")).uniqueResult();
        System.out.println(count); //统计总行数:20


        //统计id最大值
        Integer max = (Integer) session.createCriteria(Student.class).setProjection(Projections.max("id")).uniqueResult();
        System.out.println(max);

        //统计age最小值
        Integer min = (Integer) session.createCriteria(Student.class).setProjection(Projections.min("age")).uniqueResult();
        System.out.println(min);

        //统计score平均值
        Double avg = (Double) session.createCriteria(Student.class).setProjection(Projections.avg("score")).uniqueResult();
        System.out.println(avg);

        //统计总分数
        Long sum = (Long) session.createCriteria(Student.class).setProjection(Projections.sum("score")).uniqueResult();
        System.out.println(sum);


        transaction.commit();
    }

    @Test
    public void test8() {
        //分组查询
        Transaction transaction = session.beginTransaction();
        List list = session.createCriteria(Student.class)
                .setProjection(Projections.groupProperty("age"))
                .list();

        list.forEach(System.out::println);

        transaction.commit();
    }
}
