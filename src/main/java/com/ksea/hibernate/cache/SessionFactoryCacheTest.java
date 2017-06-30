package com.ksea.hibernate.cache;

import com.ksea.hibernate.relevance.Country;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ksea on 2017/6/30.
 * Hibernate中SessionFactory二级缓存
 */
public class SessionFactoryCacheTest {
    private Session session;

    @Before
    public void before() {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate/config/hibernate.cfg.xml").build();
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        session = sessionFactory.getCurrentSession();
    }

    @Test//二级缓存测试
    public void test1() {
        Transaction transaction = session.beginTransaction();
        try {
            //第一次查询，数据将发起SQL从DB中查询出数据
            Country country1 = session.get(Country.class, 1);
            System.out.println(country1);
            //第二次查询，数据将从一级缓存session中获取数据
            Country country2 = session.get(Country.class, 1);
            System.out.println(country2);

            //清空一级缓存 session
            session.clear();

            //第三次查询，如果数据没有发起SQL，未有从数据库中查询，即是从二级缓存中查询的数据
            Country country3 = session.get(Country.class, 1);
            System.out.println(country3);

            /**
             * 从结果中 sql只发出了一次
             *
             */

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }


    }


    @Test//二级缓存中Query缓存查询
    public void test2() {
        Transaction transaction = session.beginTransaction();
        try {
            String hql = " from  Country  where id=1";

            //第一次查询
            Country country1 = (Country) session.createQuery(hql)
                                      .setCacheable(true)//这里必须设置cacheable开启缓存
                                      .uniqueResult();
            System.out.println(country1);
            //第二次查询，
            Country country2 = (Country) session.createQuery(hql)
                    .setCacheable(true)//这里必须设置cacheable开启缓存
                    .uniqueResult();
            System.out.println(country2);


            //第三次查询，
            Country country3 =(Country) session.createQuery(hql)
                    .setCacheable(true)//这里必须设置cacheable开启缓存
                    .uniqueResult();
            System.out.println(country3);

            /**
             * 从结果中 sql只发出了一次
             * 总结
             *   Query查询默认是是关闭了从缓存中查询
             *   如需要从缓存中查询 第一步需配置： <property name="cache.use_query_cache">true</property>
             *                  第二步在使用Query查询的时候必须设置缓存激活 .setCacheable(true)
             *
             */

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }


    }
}
