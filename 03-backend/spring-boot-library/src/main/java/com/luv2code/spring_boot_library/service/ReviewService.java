package com.luv2code.spring_boot_library.service;

import com.luv2code.spring_boot_library.dao.BookRepository;
import com.luv2code.spring_boot_library.dao.ReviewRepository;
import com.luv2code.spring_boot_library.entity.Review;
import com.luv2code.spring_boot_library.requestmodels.ReviewRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.sql.Date;

@Service
@Transactional
public class ReviewService {
    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    public void postReview(String userEmail, ReviewRequest reviewRequest) throws Exception{
        Review validateReview = this.reviewRepository.findByUserEmailAndBookId(userEmail,reviewRequest.getBookId());
        if(validateReview != null){
            throw new Exception("Review already created!");
        }
        Review review = new Review();
        review.setUserEmail(userEmail);
        review.setBookId(reviewRequest.getBookId());
        review.setRating(reviewRequest.getRating());
        if(reviewRequest.getReviewDescription().isPresent()){
            review.setReviewDescription(reviewRequest.getReviewDescription().map(
                    Object::toString
            ).orElse(null));
        }

        review.setDate(Date.valueOf(LocalDate.now()));
        this.reviewRepository.save(review);
    }

    public boolean userReviewListed(String userEmail,Long bookId){
        Review review = reviewRepository.findByUserEmailAndBookId(userEmail,bookId);
        if(review != null){
            return true;
        }
        else{
            return false;
        }
    }
}
