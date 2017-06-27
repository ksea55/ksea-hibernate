package com.ksea.hibernate.test;

import com.ksea.hibernate.bo.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by mexican on 2017/6/27.
 * 总结Hibernate中多个语句同时执行的情况下
 * 在Hibernate默认执行顺序是insert update delete
 * 如果想要改变这种顺序，按照自己写的程序的先后顺序进行执行
 * 则需要直接flush方法,session.flush()
 *
 *
 */
public class HibernateTest {

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
    public void save() {

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

    @Test
    public void delete() {
        Transaction transaction = session.beginTransaction();
        try {
            Student student = new Student("jacky", 21, 100);
            //hibernate在做删除的时候，是根据id进行删除的因此是必须要id的
            student.setId(1);
            session.delete(student);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }


    @Test
    public void update() {

        //开启事务
        Transaction transaction = session.beginTransaction();
        try {
            Student student = new Student("mexican", 22, 101);
            student.setId(2);
            session.update(student);
            transaction.commit();
        } catch (Exception e) {

            e.printStackTrace();
            transaction.rollback();
        }
    }


    @Test
    public void saveOrUpdate() {

        //开启事务
        Transaction transaction = session.beginTransaction();
        try {
            Student student = new Student("ksea", 27, 100);
            student.setId(3);
            session.saveOrUpdate(student); //saveOrUpdate方法，在对象没有Id的情况下执行的是insert语句，有Id的情况下执行的是update语句
            transaction.commit();
        } catch (Exception e) {

            e.printStackTrace();
            transaction.rollback();
        }
    }


    @Test
    public void get() {

        //开启事务
        Transaction transaction = session.beginTransaction();
        try {
            Student student = session.get(Student.class, 2);
            System.out.println(student);
            /*
            * 总结get方法，get方法每次都会直接访问数据库，当查询的对象不不存在的时候，会直接返回null
            * 在hibernate中查询也必须开启事务，否者会报org.hibernate.HibernateException: get is not valid without active transaction异常
            * */
            transaction.commit();
        } catch (Exception e) {

            e.printStackTrace();
            transaction.rollback();
        }
    }


    @Test
    public void load() {

        //开启事务
        Transaction transaction = session.beginTransaction();
        try {
            Student student = session.load(Student.class, 22);
            System.out.println(student);
            /**
             * 总结Load方法，Load方法查询是有延迟加载的，是在真正用到这个数据的时候才回去查询数据库，而不是一开始就查询数据库
             * 如这里如果不打印student是不会执行查询语句的，当打印student的时候才回去执行数据库查询
             * Load方法在查询的数据中，如果未查询到该数据这会抛出org.hibernate.ObjectNotFoundException: No row with the given identifier exists:异常
             *
             */
            transaction.commit();
        } catch (Exception e) {

            e.printStackTrace();
            transaction.rollback();
        }
    }
}