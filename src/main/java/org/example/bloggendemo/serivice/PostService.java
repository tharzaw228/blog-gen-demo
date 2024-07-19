package org.example.bloggendemo.serivice;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bloggendemo.dao.CategoryDao;
import org.example.bloggendemo.dao.PostDao;
import org.example.bloggendemo.dao.UserDao;
import org.example.bloggendemo.entity.Category;
import org.example.bloggendemo.entity.Post;
import org.example.bloggendemo.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final CategoryDao categoryDao;
    private final UserDao userDao;
    private final PostDao postDao;

    public void saveUser(User user) {
        userDao.save(user);
    }

    public void saveCategory(Category category) {
        categoryDao.save(category);
    }

    public void deleteCategory(Long id) {
        Category category1 = getCategoryById(id);
        categoryDao.deleteById(id);
    }

    public void savePost(Post post) {
        Category category = categoryDao.findById(post.getCategory().getId()).get();
        User user = userDao.findById(post.getUser().getId()).get();
        post.setCategory(category);
        post.setUser(user);
        postDao.save(post);
    }

    public List<Post> getAllPosts() {
        return postDao.findAll();
    }

    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryDao.findById(id).orElseThrow(() -> new RuntimeException("Category id "+ id + "cannot be found"));
    }

    public User getUserById(Long id) {
        return userDao.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + "is not found"));
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Transactional
    public void updateUserById(Long id, User user) {
        User user1 = this.getUserById(id);
        user1.setId(id);
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setDateRegistered(user.getDateRegistered());
    }

    public void deleteUserById(Long id) {
        User user = this.getUserById(id);
        userDao.delete(user);
    }

    @Transactional
    public void updateCategoryById(Long id, Category category) {
        Category category1 = getCategoryById(id);

            category1.setId(id);
            category1.setTitle(category.getTitle());
            category1.setDatePosted(category.getDatePosted());
    }

    public Post getPostById(Long id) {
        return postDao.findById(id).orElseThrow(() -> new RuntimeException("Post id "+ id + "cannot be found"));
    }

    @Transactional
    public void updatePostById(Long id, Post post) {
        Post post1 = this.getPostById(id);
        Category category = categoryDao.findById(post.getCategory().getId()).get();
        User user = userDao.findById(post.getUser().getId()).get();
        post1.setCategory(category);
        post1.setUser(user);
        post1.setTitle(post.getTitle());
        post1.setBody(post.getBody());
        post1.setDatePosted(post.getDatePosted());
    }

    public void deletePostById(Long id) {
        Post post = this.getPostById(id);
        postDao.delete(post);
    }

}
