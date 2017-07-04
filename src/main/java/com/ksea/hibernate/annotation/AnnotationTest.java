package com.ksea.hibernate.annotation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ksea on 2017/7/3.
 */
public class AnnotationTest {
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

        try {

            Book book = new Book("JAVA", "java_");
            session.save(book);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }


    @Test //one-to-many
    public void test2() {
        Transaction transaction = session.beginTransaction();

        try {

            Book book1 = new Book("JAVA", "java_");
            Book book2 = new Book("C++", "_C++");

            Reader reader = new Reader("jacky");
            reader.getBooks().add(book1);
            reader.getBooks().add(book2);


            session.save(reader);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }


    @Test //many-to-one
    public void test3() {
        Transaction transaction = session.beginTransaction();

        try {

            Reader reader = new Reader("ksea");
            Book book1 = new Book("Spring", "_spring");
            Book book2 = new Book("hibernate", "_hibernate");

            book1.setReader(reader);
            book2.setReader(reader);

            session.save(book1);
            session.save(book2);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }



    @Test //oneself 自关联
    public void test4() {

        Transaction transaction = session.beginTransaction();
        try {
            News parentNewsAct = new News("体育栏目", "体育栏目体育");

            News childNewsAct1 = new News("足球栏目", "足球栏目足球");
            News childNewsAct2 = new News("篮球栏目", "篮球栏目篮球");

            childNewsAct1.setParentNewAct(parentNewsAct);
            childNewsAct2.setParentNewAct(parentNewsAct); //此处设置关联

            session.save(childNewsAct1);//many-to-one  此处保存的是多方，由多方进行关联关系维护
            session.save(childNewsAct2);//many-to-one  此处保存的是多方，由多方进行关联关系维护

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }

    }

    @Test //many-to-many
    public void test5() {
        Transaction transaction = session.beginTransaction();

        try {

            Student s1 = new Student("zs", 21, 22);
            Student s2 = new Student("lisi", 24, 52);
            Teacher t1 = new Teacher("Kim");
            Teacher t2 = new Teacher("Ksea");

            s1.getTeachers().add(t1);
            s1.getTeachers().add(t2);
            s2.getTeachers().add(t1);
            s2.getTeachers().add(t2);

            session.save(s1);
            session.save(s2);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

}
