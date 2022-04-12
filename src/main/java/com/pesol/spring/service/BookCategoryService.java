package com.pesol.spring.service;

import java.util.List;

import com.pesol.spring.entity.BookCategory;


public interface BookCategoryService {
    
    List<BookCategory> getAll();

    BookCategory getById(Integer id);

    BookCategory getByName(String name);

    void save(BookCategory category);

    void delete(BookCategory category);
}
