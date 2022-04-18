package com.pesol.spring.service;

import java.util.List;

import com.pesol.spring.entity.Borrow;

public interface BorrowService {
    
    List<Borrow> getAll();

    Borrow getById(int id);

    void delete(Borrow borrow);

    void save(Borrow borrow);

    void update(Borrow borrow);
}
