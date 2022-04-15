package com.pesol.spring.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;


public class BookModel {
    
    @NotBlank(message = "is required")
    private String name;

    @NotNull(message = "is required")
    private Integer categoryId;

    @NotNull(message = "is required")
    @Range(min = 1, message = "value must equals or greater than 1")
    private Integer inStock;

    @NotNull(message = "is required")
    @Range(min = 1, message = "value must equals or greater than 1")
    private Double fineRate;

    public BookModel() {
        this.inStock = 1;
        this.fineRate = 1.0D;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    public Double getFineRate() {
        return fineRate;
    }

    public void setFineRate(Double fineRate) {
        this.fineRate = fineRate;
    }

    @Override
    public String toString() {
        return "BookModel [categoryId=" + categoryId + ", fineRate=" + fineRate + ", inStock=" + inStock + ", name="
                + name + "]";
    }
}
