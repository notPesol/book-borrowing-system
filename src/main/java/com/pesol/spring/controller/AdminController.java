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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookCategoryService bookCategoryService;
    
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
}
