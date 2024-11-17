package com.luv2code.spring_boot_library.responsemodels;

import com.luv2code.spring_boot_library.entity.Book;
import lombok.Data;

@Data
public class ShellCurrentLoansResponse {
    private Book book;
    private int daysLeft;

    public ShellCurrentLoansResponse(Book book,int daysLeft){
        this.book = book;
        this.daysLeft = daysLeft;
    }
}
