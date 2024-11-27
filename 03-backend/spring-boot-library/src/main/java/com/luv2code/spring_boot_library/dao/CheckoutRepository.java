package com.luv2code.spring_boot_library.dao;

import com.luv2code.spring_boot_library.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout,Long> {
    Checkout getByUserEmailAndBookId(String userEmail,Long bookId);
    List<Checkout> getBooksByUserEmail(String userEmail);

    @Modifying
    @Query("delete from Checkout c WHERE c.bookId in :book_id")
    void deleteAllByBookId(@Param("book_id") Long bookId);
}
