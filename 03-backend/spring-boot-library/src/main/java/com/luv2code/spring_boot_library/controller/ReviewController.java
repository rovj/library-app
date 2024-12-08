package com.luv2code.spring_boot_library.controller;

import com.luv2code.spring_boot_library.requestmodels.ReviewRequest;
import com.luv2code.spring_boot_library.service.ReviewService;
import com.luv2code.spring_boot_library.utils.ExtractJWT;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("https://localhost:3000")
@RequestMapping("/api/reviews")
public class ReviewController {
    private ReviewService reviewService;
    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @PostMapping("/secure")
    public void secureReview(@RequestHeader(value="Authorization") String token,
                             @RequestBody ReviewRequest reviewRequest) throws Exception{
        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
        if(userEmail == null){
            throw new Exception("User email is missing");
        }
        this.reviewService.postReview(userEmail,reviewRequest);
    }

    @GetMapping("/secure/user/book")
    public boolean reviewBookByUser(@RequestHeader(value="Authorization") String token,
                                 @RequestParam Long bookId) throws Exception{
        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
        if(userEmail == null){
            throw new Exception("User email is missing");
        }
        return this.reviewService.userReviewListed(userEmail,bookId);
    }
}
