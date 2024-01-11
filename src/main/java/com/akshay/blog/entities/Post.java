package com.akshay.blog.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(name = "post_title", length = 100, nullable = false)
    private String title;

    @Column(name = "post_content", length = 1000, nullable = false)
    private String content;

    private String imageName;

    private Date addDate;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;
}