package org.example.bloggendemo.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bloggendemo.entity.Category;
import org.example.bloggendemo.entity.Post;
import org.example.bloggendemo.entity.User;
import org.example.bloggendemo.serivice.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class BlogController {

    private final PostService postService;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("user", new User());
        model.addAttribute("post", new Post());
        model.addAttribute("users", postService.getAllUsers());
        model.addAttribute("categories", postService.getAllCategories());
        return "pages/home";
    }

    @PostMapping("/save-category")
    public String saveCategory(Category category, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "/pages/home";
        }
        postService.saveCategory(category);
        return "redirect:/home";

    }



    @GetMapping("/list-category")
    public String listCategory(@RequestParam(value = "id", required = false)Long id, Model model) {
        model.addAttribute("categories", postService.getAllCategories());

        return "pages/category";
    }

    Long categoryId;

    @GetMapping("/update-category")
    public String updateCategory( @RequestParam("id")Long id, Model model) {
        try {
            model.addAttribute("category", postService.getCategoryById(id));
            categoryId = id;
            return "pages/categoryUpdate";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "/pages/error";
        }

    }

    @PostMapping("/update-category")
    public String doUpdateCategory(Category category, BindingResult bindingResult,Model model) {
        if(bindingResult.hasErrors()) {
            return "pages/categoryUpdate";
        }
        postService.updateCategoryById(categoryId, category);
        return "redirect:/list-category";
    }

    @GetMapping("/delete-category")
    public String deleteCategory(@RequestParam("id")Long id, Model model) {
        try {
            postService.deleteCategory(id);
            return "redirect:/list-category";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "pages/error";
        }

    }





    @PostMapping("/save-user")
    public String saveUer(User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "pages/home";
        }
        postService.saveUser(user);
        return "redirect:/list-user";
    }

    @PostMapping("/save-post")
    public String savePost(Post post, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "redirect:/home";
        }
        postService.savePost(post);
        return "redirect:/list-post";
    }

    @GetMapping("list-user")
    public String listUser(Model model) {
        model.addAttribute("users", postService.getAllUsers());
        return "pages/user";
    }

    Long userId;

    @GetMapping("/user-update")
    public String userUpdateForm(@RequestParam("id")Long id, Model model) {
        try {
            model.addAttribute("user", postService.getUserById(id));
            userId = id;
            return "pages/userUpdate";

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "/pages/error";
        }
    }

    @PostMapping("/user-update")
    public String userUpdate(User user, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "/pages/userUpdate";
        }else {
            postService.updateUserById(userId, user);
            return "redirect:/list-user";
        }
    }

    @GetMapping("user-delete")
    public String deleteUser(@RequestParam("id")Long id, Model model) {
        try {
            postService.deleteUserById(id);
            return "redirect:/list-user";
        }catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "pages/error";
        }
    }

    @GetMapping("list-post")
    public String listPost(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "pages/post";

    }

    @GetMapping("post-update")
    public String postUpdateForm(@RequestParam("id")Long id, Model model) {
        try {
            model.addAttribute("post", postService.getPostById(id));
            model.addAttribute("users", postService.getAllUsers());
            model.addAttribute("categories", postService.getAllCategories());
            postId = id;
            return "pages/postUpdate";
        }catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "pages/error";
        }


    }

    Long postId;

    @PostMapping("post-update")
    public String postUpdate(Post post, BindingResult bindingResult,Model model) {
        if(bindingResult.hasErrors()) {
            return "pages/postUpdate";
        }else {
            postService.updatePostById(postId, post);
            return "redirect:/list-post";
        }
    }

    @GetMapping("post-delete")
    public String postDelete(@RequestParam("id")Long id, Model model) {
        try {
            postService.deletePostById(id);
            return "redirect:/list-post";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "pages/error";
        }
    }
}
