package com.pesol.spring.controller;

import javax.validation.Valid;

import com.pesol.spring.entity.BookCategory;
import com.pesol.spring.service.BookCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookCategoryService bookCategoryService;
    
    @GetMapping
    public String renderHome() {
        return "admin/home";
    }

    @GetMapping("/add-category")
    public String renderAddCategory(Model model) {
        model.addAttribute("tempCategory", new BookCategory());
        return "admin/add-category";
    }

    @PostMapping("/add-category")
    public String processAddCategory(@Valid @ModelAttribute BookCategory tempCategory,
             BindingResult bindingResult, Model model) {
        
        if (bindingResult.hasErrors()) {
            return "admin/add-category";
        }

        BookCategory category = bookCategoryService.getByName(tempCategory.getName());
        if (category != null) {
            model.addAttribute("tempCategory", new BookCategory());
            model.addAttribute("errorMessage", "Category name is already exist");
            return "admin/add-category";
        }

        //bookCategoryService.save(tempCategory);

        return "redirect:/admin/add-category?success";
    }
}
