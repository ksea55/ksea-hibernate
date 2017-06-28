package com.ksea.hibernate.relevance;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mexican on 2017/6/28.
 * 课程
 */
public class Course {
    private Integer id;
    private String name;
    private Set<Teacher> teachers = new HashSet<>(); //one-to-many 一个课程可以被多个老师选择

    public Course() {
    }

    public Course(String name) {
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

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
