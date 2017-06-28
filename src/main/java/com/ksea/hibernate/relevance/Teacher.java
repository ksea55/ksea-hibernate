package com.ksea.hibernate.relevance;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mexican on 2017/6/
 * 老师
 */
public class Teacher {
    private Integer id;
    private String name;
    private Set<Course> courses = new HashSet<>(); //one-to-many 一个老师可以教多个课程

    public Teacher() {
    }

    public Teacher(String name) {
        this.name = name;
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

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
