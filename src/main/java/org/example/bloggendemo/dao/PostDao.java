package org.example.bloggendemo.dao;

import org.example.bloggendemo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostDao extends JpaRepository<Post, Long> {
}
