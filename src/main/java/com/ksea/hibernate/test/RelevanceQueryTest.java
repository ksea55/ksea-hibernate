package com.ksea.hibernate.test;

import com.ksea.hibernate.relevance.Country;
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
 * Created by ksea on 2017/6/29.
 */
public class RelevanceQueryTest {

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


    @Test //left join
    public void test1() {
        Transaction transaction = session.beginTransaction();
        try {
            //这就是hql的写法，关联查询
            String hql = "from Country c  left join c.ministers";
            //Country country = (Country) session.createQuery(hql).uniqueResult();
            // System.out.println(country);
            /***上面这种方式执行的查询语句，其异常信息说返回的不是一个唯一的结果
             *
             *  select
             country0_.cid as cid1_0_0_,
             ministers1_.mid as mid1_3_1_,
             country0_.name as name2_0_0_,
             ministers1_.name as name2_3_1_,
             ministers1_.countryId as countryI3_3_1_
             from
             Country country0_
             left outer join
             Minister ministers1_
             on country0_.cid=ministers1_.countryId
             org.hibernate.NonUniqueResultException: query did not return a unique result:
             *
             */


            //   List<Country> list = session.createQuery(hql).list();
            // System.out.println(list.get(0).getName());
            /***
             * 这里会说类型转换出错
             * Hibernate:
             select
             country0_.cid as cid1_0_0_,
             ministers1_.mid as mid1_3_1_,
             country0_.name as name2_0_0_,
             ministers1_.name as name2_3_1_,
             ministers1_.countryId as countryI3_3_1_
             from
             Country country0_
             left outer join
             Minister ministers1_
             on country0_.cid=ministers1_.countryId
             java.lang.ClassCastException: [Ljava.lang.Object; cannot be cast to com.ksea.hibernate.relevance.Country
             *
             *
             */


            //正确的方式,left join 返回的是一个List<Object[]> 是一个Object[]数组
            List<Object[]> list = session.createQuery(hql).list();
            System.out.println(list.get(0)[0] + "--" + list.get(0)[1]);
            /**
             * 从结果我可以看出 Object[]=[Country,Minister]
             * Hibernate:
             select
             country0_.cid as cid1_0_0_,
             ministers1_.mid as mid1_3_1_,
             country0_.name as name2_0_0_,
             ministers1_.name as name2_3_1_,
             ministers1_.countryId as countryI3_3_1_
             from
             Country country0_
             left outer join
             Minister ministers1_
             on country0_.cid=ministers1_.countryId
             Hibernate:
             select
             ministers0_.countryId as countryI3_3_0_,
             ministers0_.mid as mid1_3_0_,
             ministers0_.mid as mid1_3_1_,
             ministers0_.name as name2_3_1_,
             ministers0_.countryId as countryI3_3_1_
             from
             Minister ministers0_
             where
             ministers0_.countryId=?
             Country{cid=1, name='AiSha', ministers=[Minister{mid=1, name='USA'}, Minister{mid=2, name='PuJing'}]}--Minister{mid=1, name='USA'}
             */
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }


    @Test //left join fetch 能一次查询出来 比left join更合理
    public void test2() {
        String hql = "from Country c  left join fetch c.ministers";
        Transaction transaction = session.beginTransaction();
        try {

            Country country = (Country) session.createQuery(hql).uniqueResult();
            System.out.println(country);
            /***
             *
             *     select
             country0_.cid as cid1_0_0_,
             ministers1_.mid as mid1_3_1_,
             country0_.name as name2_0_0_,
             ministers1_.name as name2_3_1_,
             ministers1_.countryId as countryI3_3_1_,
             ministers1_.countryId as countryI3_3_0__,
             ministers1_.mid as mid1_3_0__
             from
             Country country0_
             left outer join
             Minister ministers1_
             on country0_.cid=ministers1_.countryId
             Country{cid=1, name='AiSha', ministers=[Minister{mid=2, name='PuJing'}, Minister{mid=1, name='USA'}]}
             *
             */
            transaction.commit();
            ;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }

    }

}
