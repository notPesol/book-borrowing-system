package com.pesol.spring.service;

import java.util.List;

import com.pesol.spring.entity.Book;
import com.pesol.spring.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book getByName(String name) {
        return bookRepository.findByName(name);
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }
    
    
}
