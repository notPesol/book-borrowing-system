package com.pesol.spring.service;

import java.util.List;

import com.pesol.spring.entity.Borrow;
import com.pesol.spring.repository.BorrowRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BorrowServiceImpl implements BorrowService{

    @Autowired
    private BorrowRepository borrowRepository;

    @Override
    public List<Borrow> getAll() {
        return borrowRepository.findAll();
    }

    @Override
    public Borrow getById(int id) {
        return borrowRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Borrow borrow) {
        borrowRepository.delete(borrow);
        
    }

    @Override
    public void save(Borrow borrow) {
        borrowRepository.save(borrow);
        
    }

    @Override
    public void update(Borrow borrow) {
        borrowRepository.save(borrow);
        
    }
    
}
