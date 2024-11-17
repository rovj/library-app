package com.luv2code.spring_boot_library.service;

import com.luv2code.spring_boot_library.dao.BookRepository;
import com.luv2code.spring_boot_library.dao.CheckoutRepository;
import com.luv2code.spring_boot_library.entity.Book;
import com.luv2code.spring_boot_library.entity.Checkout;
import com.luv2code.spring_boot_library.responsemodels.ShellCurrentLoansResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class BookService {
    private BookRepository bookRepository;
    private CheckoutRepository checkoutRepository;

    public BookService(BookRepository bookRepository,CheckoutRepository checkoutRepository){
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
    }

    public Book checkoutBook(String userEmail,Long bookId) throws Exception{
        Optional<Book> book = this.bookRepository.findById(bookId);
        Checkout checkout = this.checkoutRepository.getByUserEmailAndBookId(userEmail,bookId);
        if(!book.isPresent() || checkout!=null || book.get().getCopiesAvailable()<=0){
            throw new Exception("Book not present!");
        }
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        this.bookRepository.save(book.get());
        Checkout newCheckout = new Checkout(userEmail, LocalDate.now().toString(),LocalDate.now().plusDays(7).toString(),bookId);
        this.checkoutRepository.save(newCheckout);
        return book.get();
    }

    public Boolean isCheckoutByUser(String userEmail,Long bookId){
        Checkout checkout = this.checkoutRepository.getByUserEmailAndBookId(userEmail,bookId);
        if(checkout!=null){
            return true;
        }
        else{
            return false;
        }
    }

    public int getLoanCount(String userEmail){
        List<Checkout> list = this.checkoutRepository.getBooksByUserEmail(userEmail);
        return list.size();
    }

    public List<ShellCurrentLoansResponse> currentLoans(String userEmail) throws Exception{
        List<ShellCurrentLoansResponse> shellCurrentLoansResponses = new ArrayList<>();
        List<Checkout> checkoutList = this.checkoutRepository.getBooksByUserEmail(userEmail);
        List<Long> bookIdList = new ArrayList<>();
        for(Checkout checkout : checkoutList){
            bookIdList.add(checkout.getBookId());
        }
        List<Book> bookList = this.bookRepository.findBooksByBookIds(bookIdList);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Book book : bookList){
            Optional<Checkout> checkout = checkoutList.stream()
                                                      .filter(x -> x.getBookId() == book.getId()).findFirst();
            if(checkout.isPresent()){
                Date d1 = sdf.parse(checkout.get().getCheckoutDate());
                Date d2 = sdf.parse(LocalDate.now().toString());

                TimeUnit unit = TimeUnit.DAYS;
                long difference_in_time = unit.convert(d1.getTime()-d2.getTime(),TimeUnit.MILLISECONDS);
                shellCurrentLoansResponses.add(new ShellCurrentLoansResponse(book,(int)difference_in_time));
            }
        }
        return shellCurrentLoansResponses;
    }
}
