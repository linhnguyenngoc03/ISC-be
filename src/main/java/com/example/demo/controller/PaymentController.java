package com.example.demo.controller;

import com.example.demo.repository.PaymentRepository;
import com.example.demo.model.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @GetMapping("/allPayment")
    public ResponseEntity<Object> getAllPayment() throws Exception {
        List<Payment> paymentList = PaymentRepository.getAllPayment();
        if (paymentList.size() != 0) {
            return ResponseEntity.ok().body(paymentList);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getPaymentById")
    public ResponseEntity<Object> getPaymentById(@RequestParam int paymentId) throws Exception {
        Payment payment = PaymentRepository.getPaymentById(paymentId);
        if (payment.getPaymentId() != 0) return ResponseEntity.ok().body(payment);
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/createPayment")
    public ResponseEntity<Object> createPayment(@RequestBody Payment payment) throws Exception {
        if(PaymentRepository.createPayment(payment)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/updatePayment")
    public ResponseEntity<Object> updatePayment(@RequestBody Payment payment) throws Exception {
        if(PaymentRepository.updatePayment(payment)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/deletePayment")
    public ResponseEntity<Object> deletePayment(@RequestParam int[] paymentId) throws Exception {
        if(PaymentRepository.deletePayment(paymentId)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }
}
