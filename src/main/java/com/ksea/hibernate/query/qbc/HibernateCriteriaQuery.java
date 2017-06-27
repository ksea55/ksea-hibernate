package com.ksea.hibernate.query.qbc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Before;

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
}
