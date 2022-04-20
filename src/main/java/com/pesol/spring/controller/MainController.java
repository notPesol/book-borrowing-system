package com.pesol.spring.controller;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import com.pesol.spring.entity.Book;
import com.pesol.spring.entity.Borrow;
import com.pesol.spring.entity.User;
import com.pesol.spring.model.MyUserDetail;
import com.pesol.spring.service.BookService;
import com.pesol.spring.service.BorrowService;
import com.pesol.spring.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class MainController {

    private static Logger logger = LoggerFactory.getLogger(MainController.class);
    
    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired BorrowService borrowService;

    @GetMapping("/")
    public String renderHome(Model model) {

        model.addAttribute("demoDate", Date.valueOf(LocalDate.now()));

        return "index";
    }

    // for render book list page for user
    @GetMapping("/book")
    public String renderBook(Model model) {

        model.addAttribute("books", bookService.getAll());

        return "book";
    }

    @PostMapping("/book/{id}")
    public String processBorrowBook(@PathVariable int id, Model model, Authentication authentication) {
        
        Book book = bookService.getById(id);

        if (book != null && book.getInStock() > 0) {
            boolean isBorrowed = false;
            MyUserDetail userDetail = (MyUserDetail) authentication.getPrincipal();

            User user = userService.getById(userDetail.getId());
            if(user != null) {
                // send to borrow service and process it
                isBorrowed = borrowService.borrow(user, book);
            }

            if (isBorrowed) {
                return "redirect:/book?success";
            }else {
                return "redirect:/book?failed";
            }
        }

        return "redirect:/book";
    }

    @GetMapping("/borrow")
    public String renderBorrow(Model model, Authentication authentication) {

        MyUserDetail userDetail = (MyUserDetail) authentication.getPrincipal();
        User user = userService.getById(userDetail.getId());
        List<Borrow> borrows = user.getBorrows();
        model.addAttribute("borrows", borrows);

        return "borrow";
    }
}
