package org.example.bloggendemo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "blog_user")
@Getter
@Setter

public class User extends IdClass {
    private String name;
    private String email;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateRegistered;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post) {
        post.setUser(this);
        posts.add(post);
    }
}
