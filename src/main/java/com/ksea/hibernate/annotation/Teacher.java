package com.ksea.hibernate.annotation;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mexican on 2017/6/
 * 老师
 */
@Entity
@Table
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    /**
     *
     * 在使用manyTomany中，双向关联的情况下，一方必须放弃主动维护权，否者会创建2个中间表
     */
    @ManyToMany(targetEntity = Student.class, cascade = CascadeType.ALL,mappedBy = "teachers")
    private Set<Student> students = new HashSet<>();

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

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
