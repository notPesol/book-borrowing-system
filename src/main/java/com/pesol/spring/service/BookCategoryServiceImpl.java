package com.pesol.spring.service;

import java.util.List;
import java.util.Optional;

import com.pesol.spring.entity.BookCategory;
import com.pesol.spring.repository.BookCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookCategoryServiceImpl implements BookCategoryService{

    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    @Override
    public List<BookCategory> getAll() {
        return bookCategoryRepository.findAll();
    }

    @Override
    public BookCategory getById(Integer id) {
        Optional<BookCategory> result = bookCategoryRepository.findById(id);
        return result.orElse(null);
    }

    @Override
    public BookCategory getByName(String name) {
        return bookCategoryRepository.findByName(name);
    }

    @Override
    public void save(BookCategory category) {
        bookCategoryRepository.save(category);
        
    }

    @Override
    public void delete(BookCategory category) {
        bookCategoryRepository.delete(category);
        
    }
    


}
