package com.ksea.hibernate.annotation;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mexican on 2017/6/28.
 * 新闻节目类
 */
@Entity
@Table
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //新闻节目id
    private String name; //新闻节目名称
    private String content; //新闻节目内容

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pid")
    private Set<News> childNewActs = new HashSet<>();//一个电台可以有多个节目

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pid")
    private News parentNewAct; //多个节目属于一个电台


    public News() {
    }

    public News(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Set<News> getChildNewActs() {
        return childNewActs;
    }

    public void setChildNewActs(Set<News> childNewActs) {
        this.childNewActs = childNewActs;
    }

    public News getParentNewAct() {
        return parentNewAct;
    }

    public void setParentNewAct(News parentNewAct) {
        this.parentNewAct = parentNewAct;
    }

    @Override
    public String toString() {
        return "NewsAct{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
