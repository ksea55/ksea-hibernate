package com.ksea.hibernate.relevance;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mexican on 2017/6/28.
 * 新闻节目类
 */
public class NewsAct {
    private Integer id; //新闻节目id
    private String name; //新闻节目名称
    private String content; //新闻节目内容

    //one-to-many
    private Set<NewsAct> childNewActs = new HashSet<>();//一个电台可以有多个节目

    //many-to-one
    private NewsAct parentNewAct; //多个节目属于一个电台


    public NewsAct() {
    }

    public NewsAct(String name, String content) {
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


    public Set<NewsAct> getChildNewActs() {
        return childNewActs;
    }

    public void setChildNewActs(Set<NewsAct> childNewActs) {
        this.childNewActs = childNewActs;
    }

    public NewsAct getParentNewAct() {
        return parentNewAct;
    }

    public void setParentNewAct(NewsAct parentNewAct) {
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
