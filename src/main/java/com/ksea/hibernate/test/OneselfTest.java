package com.ksea.hibernate.test;

import com.ksea.hibernate.relevance.NewsAct;
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
 * 自关联测试
 */
public class OneselfTest {
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

    @Test //one-to-many 一方维护关联关系
    public void test1() {

        Transaction transaction = session.beginTransaction();
        try {
            NewsAct parentNewsAct = new NewsAct("体育栏目", "体育栏目体育");

            NewsAct childNewsAct1 = new NewsAct("足球栏目", "足球栏目足球");
            NewsAct childNewsAct2 = new NewsAct("篮球栏目", "篮球栏目篮球");

            parentNewsAct.getChildNewActs().add(childNewsAct1);
            parentNewsAct.getChildNewActs().add(childNewsAct2);

            session.save(parentNewsAct);//one-to-many  此处保存的是一方，由一方进行关联关系维护

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }

    }


    @Test //many-to-one 多对一关联
    public void test2() {

        Transaction transaction = session.beginTransaction();
        try {
            NewsAct parentNewsAct = new NewsAct("体育栏目", "体育栏目体育");

            NewsAct childNewsAct1 = new NewsAct("足球栏目", "足球栏目足球");
            NewsAct childNewsAct2 = new NewsAct("篮球栏目", "篮球栏目篮球");

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

}
