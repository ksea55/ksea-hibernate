package com.ksea.hibernate.relevance;

/**
 * Created by ksea on 2017/6/28.
 * 部长信息类
 */
public class Minister {
    private Integer mid;
    private String name;

    private Country country;


    public Minister() {
    }

    public Minister(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Minister{" +
                "mid=" + mid +
                ", name='" + name + '\'' +
                '}';
    }
}
