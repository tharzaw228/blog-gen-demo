package org.example.bloggendemo.dao;

import org.example.bloggendemo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, Long> {
}
