package com.pesol.spring.service;

import java.util.List;

import com.pesol.spring.entity.Book;

public interface BookService {
 
    List<Book> getAll();

    Book getById(int id);

    Book getByName(String name);

    void save(Book book);

    void delete(Book book);
}
