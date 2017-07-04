package com.ksea.hibernate.annotation;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ksea on 2017/7/4.
 */
@Entity
@Table
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    /**
     * 注意在@OneToMany中的参数中mappedBy = "reader" 表示 一方reader放弃其主动维护权，其维护权交给多方Book来进行维护
     *                        其中reader是Book类中的属性名称
     * 在添加了mappedby这个参数之后，已经放弃主动维护权  在一方Reader中@JoinColumn(name = "rid")将不能再写，否者会报错
     *
     */
    @OneToMany(targetEntity = Book.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "rid")
    private Set<Book> books = new HashSet<>();

    public Reader() {
    }

    public Reader(String name) {
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


    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Reader{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
