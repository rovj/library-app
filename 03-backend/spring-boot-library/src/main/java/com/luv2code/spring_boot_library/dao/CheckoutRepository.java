package com.luv2code.spring_boot_library.dao;

import com.luv2code.spring_boot_library.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout,Long> {
    Checkout getByUserEmailAndBookId(String userEmail,Long bookId);
    List<Checkout> getBooksByUserEmail(String userEmail);
}
