package com.pesol.spring.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.pesol.spring.entity.Book;
import com.pesol.spring.entity.Borrow;
import com.pesol.spring.entity.User;
import com.pesol.spring.repository.BookRepository;
import com.pesol.spring.repository.BorrowRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowServiceImpl implements BorrowService {

    Logger logger = LoggerFactory.getLogger(BorrowServiceImpl.class);

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BookRepository bookRepository;

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

    @Override
    public boolean borrow(User user, Book book) {

        List<Borrow> borrows = user.getBorrows();
        for (Borrow borrow : borrows) {
            // if have book in borrow list and not return
            if (borrow.getBook().equals(book) && !borrow.isReturned()) {
                return false;
            }
        }

        book.setInStock(book.getInStock() - 1);
        bookRepository.save(book);

        Borrow tempBorrow = new Borrow(
                user,
                book,
                Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusDays(7)),
                null);

        borrowRepository.save(tempBorrow);


        return true;
    }

}
