package com.pesol.spring.controller;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;

import com.pesol.spring.entity.Book;
import com.pesol.spring.entity.Borrow;
import com.pesol.spring.entity.User;
import com.pesol.spring.model.MyUserDetail;
import com.pesol.spring.service.BookService;
import com.pesol.spring.service.BorrowService;
import com.pesol.spring.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class MainController {
    
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
            MyUserDetail userDetail = (MyUserDetail) authentication.getPrincipal();

            // TODO: ค่อยมาปรับปรุงและใส่ใน service เดียวกันดีกว่า
            User user = userService.getById(userDetail.getId());
            if(user != null) {
                Borrow tempBorrow = new Borrow(
                    user, 
                    book, 
                    Date.valueOf(LocalDate.now()), 
                    Date.valueOf(LocalDate.now().plusDays(7)), 
                    null);
                
                    System.out.println(tempBorrow);

                borrowService.save(tempBorrow);

                book.setInStock(book.getInStock() - 1);
                bookService.save(book);
            }

           
            return "redirect:/book";
        }


        return "redirect:/book?success";
    }
}
