package com.pesol.spring.repository;

import com.pesol.spring.entity.BookCategory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Integer>{
    
    BookCategory findByName(String name);

    
}
