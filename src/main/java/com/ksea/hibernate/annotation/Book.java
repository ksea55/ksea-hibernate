package com.ksea.hibernate.annotation;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by ksea on 2017/7/3.
 */
@Entity //表明是实体类注解
@Table //如果不写默认就是表名默认就是类名
public class Book {
    /**
     * @Id
     * @GeneratedValue(strategy = GenerationType.IDENTITY) 这种方式是基于JPA的注解
     */
    @Id
    //  @GeneratedValue(strategy = GenerationType.IDENTITY)

    @GeneratedValue(generator = "id_")
    @GenericGenerator(name = "id_", strategy = "native") //这种方式是基于Hibernate提供的策略
    private Integer id;
    private String name;
    private String author;

    @ManyToOne(targetEntity = Reader.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "rid")
    private Reader reader;


    @Transient //该注解表明是瞬时的，该字段将不会写入到DB中
    private String desc;

    public Book() {
    }

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
