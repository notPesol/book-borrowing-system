package com.pesol.spring.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "book")
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private BookCategory category; // หมดหมู่หนังสือ

    private Integer inStock; // จำนวนในคลัง

    private Double finRate; // ค่าปรับต่อวัน

    public Book() {}

    public Book(String name, BookCategory category, Integer inStock, Double finRate) {
        this.name = name;
        this.category = category;
        this.inStock = inStock;
        this.finRate = finRate;
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

    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    public Double getFinRate() {
        return finRate;
    }

    public void setFinRate(Double finRate) {
        this.finRate = finRate;
    }

    
}
