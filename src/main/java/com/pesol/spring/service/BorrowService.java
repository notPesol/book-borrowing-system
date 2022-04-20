package com.pesol.spring.service;

import java.util.List;

import com.pesol.spring.entity.Book;
import com.pesol.spring.entity.Borrow;
import com.pesol.spring.entity.User;

public interface BorrowService {
    
    List<Borrow> getAll();

    Borrow getById(int id);

    void delete(Borrow borrow);

    void save(Borrow borrow);

    void update(Borrow borrow);

    boolean borrow(User user, Book book);
}
