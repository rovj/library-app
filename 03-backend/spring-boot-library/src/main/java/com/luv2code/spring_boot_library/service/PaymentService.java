package com.luv2code.spring_boot_library.service;

import com.luv2code.spring_boot_library.dao.PaymentRepository;
import com.luv2code.spring_boot_library.entity.Payment;
import com.luv2code.spring_boot_library.requestmodels.PaymentInfoRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PaymentService {
    private PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository, @Value("${stripe.key.secret}") String secretKey){
        this.paymentRepository = paymentRepository;
        Stripe.apiKey = secretKey;
    }

    public PaymentIntent createPaymentIntent(PaymentInfoRequest paymentInfoRequest) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String,Object> params = new HashMap<>();
        params.put("amount",paymentInfoRequest.getAmount());
        params.put("currency",paymentInfoRequest.getCurrency());
        params.put("payment_method_types",paymentMethodTypes);

        return PaymentIntent.create(params);
    }

    public ResponseEntity<String> stripePayment(String userEmail) throws Exception {
        Payment payment = this.paymentRepository.findByUserEmail(userEmail);

        if(payment == null){
            throw new Exception("Payment information is missing!");
        }

        payment.setAmount(00.00);
        this.paymentRepository.save(payment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Payment> createOrGetPayment(String userEmail){
        Payment payment = this.paymentRepository.findByUserEmail(userEmail);
        if(payment == null) {
            Payment userPayment = new Payment();
            userPayment.setAmount(00.00);
            userPayment.setUserEmail(userEmail);
            this.paymentRepository.save(userPayment);
            return new ResponseEntity<>(userPayment, HttpStatus.OK);
        }
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
}
