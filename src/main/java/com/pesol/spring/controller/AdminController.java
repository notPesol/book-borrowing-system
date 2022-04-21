package com.pesol.spring.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.validation.Valid;

import com.pesol.spring.entity.Book;
import com.pesol.spring.entity.BookCategory;
import com.pesol.spring.entity.Borrow;
import com.pesol.spring.entity.User;
import com.pesol.spring.model.BookModel;
import com.pesol.spring.service.BookCategoryService;
import com.pesol.spring.service.BookService;
import com.pesol.spring.service.BorrowService;
import com.pesol.spring.service.UserService;
import com.pesol.spring.util.DateRangeValidate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private BookCategoryService bookCategoryService;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private BorrowService borrowService;
    
    // For render admin home page
    @GetMapping
    public String renderHome() {
        return "admin/home";
    }

    // For render categories page
    @GetMapping("/category")
    public String renderCategory(Model model) {
        model.addAttribute("categories", bookCategoryService.getAll());
        return "admin/category/home";
    }

    // For render add category page
    @GetMapping("/category/add-category")
    public String renderAddCategory(Model model) {
        model.addAttribute("tempCategory", new BookCategory());
        return "admin/category/add-category";
    }

    // For process add the category page
    @PostMapping("/category/add-category")
    public String processAddCategory(@Valid @ModelAttribute(name = "tempCategory") BookCategory tempCategory,
             BindingResult bindingResult, Model model) {
        
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "admin/category/add-category";
        }

        BookCategory category = bookCategoryService.getByName(tempCategory.getName());
        if (category != null) {
            model.addAttribute("tempCategory", new BookCategory());
            model.addAttribute("errorMessage", "Category: " + tempCategory.getName() + " is already exist");
            return "admin/category/add-category";
        }

        bookCategoryService.save(tempCategory);

        return "redirect:/admin/category/add-category?success";
    }

    // For render edit category page
    @GetMapping("/category/{id}")
    public String renderEditCategory(@PathVariable int id, Model model) {

        BookCategory tempCategory = bookCategoryService.getById(id);

        if (tempCategory == null) {
            return "redirect:/admin/category";
        }

        model.addAttribute("tempCategory", tempCategory);

        return "admin/category/edit-category";
    }

    // For process update the category
    @PostMapping("/category/update")
    public String processUpdateCategory(@Valid @ModelAttribute(name = "tempCategory") BookCategory tempCategory,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "admin/category/edit-category";
        }

        BookCategory category = bookCategoryService.getByName(tempCategory.getName());
        if(category != null) {
            model.addAttribute("errorMessage", "Category " + tempCategory.getName() + " is already exist");
            return "admin/category/edit-category";
        }

        bookCategoryService.save(tempCategory);

        return "redirect:/admin/category?updated";
    }

    // For process delete the category
    @PostMapping("/category/{id}")
    public String processDeleteCategory(@PathVariable int id) {
        
        BookCategory category = bookCategoryService.getById(id);

        if(category != null) {
            bookCategoryService.delete(category);
        }

        return "redirect:/admin/category?deleted";
    }

    @GetMapping("/book")
    public String renderBooks(Model model) {
        
        model.addAttribute("books", bookService.getAll());

        return "admin/book/home";
    }

    @GetMapping("/book/add-book")
    public String renderAddBook(Model model) {

        model.addAttribute("tempCategories", bookCategoryService.getAll());
        
        model.addAttribute("tempBookModel", new BookModel());

        return "admin/book/add-book";
    }

    @PostMapping("/book/add-book")
    public String processAddBook(@Valid @ModelAttribute(name = "tempBookModel") BookModel tempBookModel,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("tempCategories", bookCategoryService.getAll());
            return "admin/book/add-book";
        }

        if (bookService.getByName(tempBookModel.getName()) != null) {
            model.addAttribute("errorMessage", "Book " + tempBookModel.getName() + " is already exist");
            model.addAttribute("tempCategories", bookCategoryService.getAll());
            model.addAttribute("tempBookModel", new BookModel());
            return "admin/book/add-book";
        }

        BookCategory category = bookCategoryService.getById(tempBookModel.getCategoryId());
        if (category == null) {
            model.addAttribute("errorMessage", "Please select the category");
            model.addAttribute("tempCategories", bookCategoryService.getAll());
            model.addAttribute("tempBookModel", new BookModel());
            return "admin/book/add-book";
        }

        // if not has errors save it
        Book book = new Book(
            tempBookModel.getName(), 
            category, 
            tempBookModel.getInStock(), 
            tempBookModel.getFineRate());

        bookService.save(book);

        return "redirect:/admin/book/add-book?success";
    }

    // for render update book page
    @GetMapping("/book/{id}")
    public String renderUpdateBook(@PathVariable int id, Model model) {

        Book tempBook = bookService.getById(id);

        if (tempBook == null) {
            return "redirect:/admin/book";
        }

        model.addAttribute("tempCategories", bookCategoryService.getAll());

        BookModel tempBookModel = new BookModel();
        tempBookModel.setId(tempBook.getId());
        tempBookModel.setName(tempBook.getName());
        tempBookModel.setCategoryId(tempBook.getCategory().getId());
        tempBookModel.setFineRate(tempBook.getFineRate());
        tempBookModel.setInStock(tempBook.getInStock());

        model.addAttribute("tempBookModel", tempBookModel);

        return "admin/book/update-book";
    } 

    @PostMapping("/book/update")
    public String processUpdate(@Valid @ModelAttribute(name = "tempBookModel") BookModel tempBookModel,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("tempCategories", bookCategoryService.getAll());
            return "admin/book/update-book";
        }

        Book tempBook = bookService.getByName(tempBookModel.getName());
        if (tempBook != null && !tempBook.getName().equals(tempBookModel.getName())) {
            model.addAttribute("errorMessage", "Book " + tempBookModel.getName() + " is already exist");
            model.addAttribute("tempCategories", bookCategoryService.getAll());
            model.addAttribute("tempBookModel", new BookModel());
            return "admin/book/update-book";
        }

        BookCategory category = bookCategoryService.getById(tempBookModel.getCategoryId());
        if (category == null) {
            model.addAttribute("errorMessage", "Please select the category");
            model.addAttribute("tempCategories", bookCategoryService.getAll());
            model.addAttribute("tempBookModel", new BookModel());
            return "admin/book/update-book";
        }

        // if not has errors save it
        Book book = new Book(
            tempBookModel.getName(), 
            category, 
            tempBookModel.getInStock(), 
            tempBookModel.getFineRate());
        // set update book id for update
        book.setId(tempBookModel.getId());

        bookService.save(book);

        return "redirect:/admin/book?updated";
    }

    // for process delete the book by id
    @PostMapping("/book/{id}")
    public String processDeleteBook(@PathVariable int id) {

        Book tempBook = bookService.getById(id);

        if (tempBook == null) {
            return "redirect:/admin/book";
        }

        bookService.delete(tempBook);

        return "redirect:/admin/book?deleted";
    } 

    // for get users list
    @GetMapping("/user")
    public String renderUserList(Model model) {

        model.addAttribute("users", userService.getAll());

        return "admin/user/home";
    }

    // for process delete the user by id
    @PostMapping("/user/{id}")
    public String processDeleteUser(@PathVariable(name = "id") int id) {
        User tempUser = userService.getById(id);
        if (tempUser == null) {
            return "redirect:/admin/user";
        }

        userService.delete(tempUser);

        return "redirect:/admin/user?deleted";
    }

    // for get borrow list
    @GetMapping("/borrow")
    public String renderBorrowList(@RequestParam(name = "sort", required = false, defaultValue = "false") boolean sort,
            Model model) {

        List<Borrow> borrows = borrowService.getAll();

        if(sort) {
            borrows.sort((b1, b2) -> {
                if(b1.isReturned()) {
                    return 1;
                }
                return -1;
            });
        }
        model.addAttribute("borrows", borrows);

        return "admin/borrow/home";
    }

    // for render process return page
    @GetMapping("/borrow/{id}")
    public String renderReturnPage(@PathVariable int id, Model model) {

        Borrow borrow = borrowService.getById(id);
        if(borrow == null) {
            return "redirect:/admin/borrow";
        }
        
        Date returnDate = Date.valueOf(LocalDate.now());
        borrow.setReturnDate(returnDate);

        Date dueDate = borrow.getDueDate();
        DateRangeValidate dateRangeValidate = new DateRangeValidate(borrow.getBorrowDate(), dueDate);
        boolean isWithinRange = dateRangeValidate.isWithinRange(returnDate);

        if (!isWithinRange) {
            long diff = returnDate.getTime() - dueDate.getTime();
            long diffDay = (diff / (1000 * 60 * 60 * 24));
            model.addAttribute("fineValue", borrow.getBook().getFineRate() * diffDay);
        }


        model.addAttribute("borrow", borrow);

        return "admin/borrow/return";
    }

    @PostMapping("/return/{id}")
    public String processReturn(@PathVariable int id, @RequestParam(name = "returnDate") Date returnDate) {
        LOGGER.info(returnDate.toString());

        Borrow borrow = borrowService.getById(id);
        if(borrow == null) {
            return "redirect:/admin/borrow";
        }
        
        borrow.setReturnDate(returnDate);
        borrow.setReturned(true);

        Book book = borrow.getBook();
        book.setInStock(book.getInStock() + 1);

        LOGGER.info(borrow.toString());

        borrowService.save(borrow);

        return "redirect:/admin/borrow";
    }
}
