package com.luv2code.spring_boot_library.controller;

import com.luv2code.spring_boot_library.requestmodels.PaymentInfoRequest;
import com.luv2code.spring_boot_library.service.PaymentService;
import com.luv2code.spring_boot_library.utils.ExtractJWT;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment/secure")
@CrossOrigin("https://localhost:3000")
public class PaymentController {
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfoRequest paymentInfoRequest) throws StripeException {
        PaymentIntent paymentIntent = this.paymentService.createPaymentIntent(paymentInfoRequest);
        String paymentStr = paymentIntent.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }

    @PutMapping("/payment-complete")
    public ResponseEntity<String> stripePaymentComplete(@RequestHeader(value = "Authorization") String token) throws Exception{
        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
        if(userEmail == null){
            throw new Exception("User email is missing!");
        }
        return this.paymentService.stripePayment(userEmail);
    }
}
