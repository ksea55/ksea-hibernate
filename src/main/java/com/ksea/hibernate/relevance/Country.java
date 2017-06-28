package com.ksea.hibernate.relevance;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ksea on 2017/6/28.
 * 国家信息类
 */
public class Country {
    private Integer cid;
    private String name;

    private Set<Minister> ministers = new HashSet<>();

    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Minister> getMinisters() {
        return ministers;
    }

    public void setMinisters(Set<Minister> ministers) {
        this.ministers = ministers;
    }

    @Override
    public String toString() {
        return "Country{" +
                "cid=" + cid +
                ", name='" + name + '\'' +
                ", ministers=" + ministers +
                '}';
    }
}
