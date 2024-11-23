package com.luv2code.spring_boot_library.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "History")
@Data
public class History {
    public History() {
    }
    public History(String userEmail,String title,String author,
                   String description,String returnedDate,String checkoutDate,String img){
        this.userEmail = userEmail;
        this.title = title;
        this.author = author;
        this.description = description;
        this.returnedDate = returnedDate;
        this.checkoutDate = checkoutDate;
        this.img = img;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="user_email")
    private String userEmail;

    @Column(name="title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "description")
    private String description;

    @Column(name = "checkout_date")
    private String checkoutDate;

    @Column(name = "returned_date")
    private String returnedDate;

    @Column(name = "img")
    private String img;

}
