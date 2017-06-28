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
    public void test1() {
        //HQL 查询全部
        Transaction transaction = session.beginTransaction();
        List list = session.createQuery("from Student").list();
        System.out.println(list);

        List<Student> students = session.createQuery("from Student", Student.class).list();
        System.out.println(students);

        transaction.commit();
    }

    @Test
    public void test2() {
        //HQL排序查询
        Transaction transaction = session.beginTransaction();
        List list = session.createQuery("from Student order by age desc").list();
        list.forEach(System.out::println);

        transaction.commit();
    }


    @Test
    public void test3() {
        //HQL动态绑定参数，根据条件查询数据
        Transaction transaction = session.beginTransaction();

        //第一种
        String hql = "from Student where age>? and score >?";
        List list = session.createQuery(hql)
                .setInteger(0, 30)
                .setInteger(1, 85).list();
        list.forEach(System.out::println);

        System.out.println("-------------------------------------------");
        //第二种
        hql = "from Student where age>:age and score >:score";
        list = session.createQuery(hql)
                .setInteger("age", 30)
                .setInteger("score", 85).list();
        list.forEach(System.out::println);


        //第三种
        hql = "from Student where age>:age and score >:score";
        list = session.createQuery(hql)
                .setParameter("age", 30)
                .setParameter("score", 85).list();
        list.forEach(System.out::println);


        transaction.commit();
    }

    @Test
    public void test4() {
        //HQL分页查询
        Transaction transaction = session.beginTransaction();
        List list = session.createQuery("from Student")
                .setFirstResult((2 - 1) * 5) //startIndex=(startIndex-1)*pageSize
                .setMaxResults(5) //pageSize
                .list();
        list.forEach(System.out::println);

        transaction.commit();
    }


    @Test
    public void test5() {
        //HQL 中的like模糊查询
        Transaction transaction = session.beginTransaction();

        String hql = "from Student where name like :name";
        List list = session.createQuery(hql)
                .setParameter("name", "%3%")
                .list();
        list.forEach(System.out::println);

        transaction.commit();
    }

    @Test
    public void test6() {
        //HQL 根据id查询唯一数据
        Transaction transaction = session.beginTransaction();

        String hql = "from Student where id=:id";
        Student student = session.createQuery(hql, Student.class)
                .setParameter("id", 3)
                .uniqueResult();
        System.out.println(student);

        transaction.commit();
    }

    @Test
    public void test7() {
        //HQL 聚合函数查询
        Transaction transaction = session.beginTransaction();

        String hql = "select count(*) from Student";
        Long count = session.createQuery(hql, Long.class)
                .uniqueResult();
        System.out.println(count); //总条数:20


        //最大值
        hql = "select  max(id) from Student";
        Integer max = session.createQuery(hql, Integer.class).uniqueResult();
        System.out.println(max); //最大值


        //最小值
        hql = "select  min(age) from Student";
        Integer min = session.createQuery(hql, Integer.class).uniqueResult();
        System.out.println(min);

        //平均值
        hql = "select  avg(score) from Student";
        Double avg = session.createQuery(hql, Double.class).uniqueResult();
        System.out.println(avg);

        //求和
        hql = "select  sum (score) from Student";
        Long sum = session.createQuery(hql, Long.class).uniqueResult();
        System.out.println(sum);


        transaction.commit();
    }

    @Test
    public void test8() {
        //分组查询
        Transaction transaction = session.beginTransaction();
        String hql = " from Student group by  age";
        List<Student> list = session.createQuery(hql, Student.class).list();
        list.forEach(System.out::println);



        hql="select  age from Student group by  age";
        List<Integer> ageList = session.createQuery(hql, Integer.class).list();
        ageList.forEach(System.out::println);

        transaction.commit();
    }
}
