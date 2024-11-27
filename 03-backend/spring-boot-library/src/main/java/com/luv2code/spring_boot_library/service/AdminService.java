package com.luv2code.spring_boot_library.service;

import com.luv2code.spring_boot_library.dao.BookRepository;
import com.luv2code.spring_boot_library.dao.CheckoutRepository;
import com.luv2code.spring_boot_library.dao.HistoryRepository;
import com.luv2code.spring_boot_library.dao.ReviewRepository;
import com.luv2code.spring_boot_library.entity.Book;
import com.luv2code.spring_boot_library.requestmodels.AddBookRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class AdminService {
    private BookRepository bookRepository;
    private CheckoutRepository checkoutRepository;
    private ReviewRepository reviewRepository;

    public AdminService(BookRepository bookRepository,CheckoutRepository checkoutRepository,ReviewRepository reviewRepository){
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
        this.reviewRepository = reviewRepository;
    }

    public void increaseBook(Long bookId) throws Exception{
        Optional<Book> book = this.bookRepository.findById(bookId);

        if(!book.isPresent()){
            throw new Exception("Book does not exist!");
        }

        book.get().setCopies(book.get().getCopies() + 1);
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);

        this.bookRepository.save(book.get());
    }

    public void decreaseBook(Long bookId) throws Exception{
        Optional<Book> book = this.bookRepository.findById(bookId);

        if(!book.isPresent() || book.get().getCopiesAvailable() <= 0 || book.get().getCopies() <= 0){
            throw new Exception("Book does not exist!");
        }

        book.get().setCopies(book.get().getCopies() - 1);
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);

        this.bookRepository.save(book.get());
    }

    public void postBook(AddBookRequest addBookRequest){
        Book book = new Book();
        book.setTitle(addBookRequest.getTitle());
        book.setCopiesAvailable(addBookRequest.getCopies());
        book.setCopies(addBookRequest.getCopies());
        book.setAuthor(addBookRequest.getAuthor());
        book.setImg(addBookRequest.getImg());
        book.setCategory(addBookRequest.getCategory());
        book.setDescription(addBookRequest.getDescription());
        this.bookRepository.save(book);
    }

    public void deleteBook(Long bookId) throws Exception{
        Optional<Book> book = this.bookRepository.findById(bookId);
        if(!book.isPresent()){
            throw new Exception("Book doesn't exist!");
        }
        this.bookRepository.delete(book.get());
        this.checkoutRepository.deleteAllByBookId(bookId);
        this.reviewRepository.deleteAllByBookId(bookId);
    }
}
