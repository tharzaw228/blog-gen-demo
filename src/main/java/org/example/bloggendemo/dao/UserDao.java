package org.example.bloggendemo.dao;

import org.example.bloggendemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Long> {
}
