package com.example.demo.controller;

import com.example.demo.repository.EmailRepository;
import com.example.demo.repository.OrderDetailsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @PostMapping("/sendEmail")
    public ResponseEntity<Object> sendEmail(@RequestParam int orderId) {
        try {
            String body = EmailRepository.messageCreate(orderId);
            String subject = "Xác nhận đơn hàng mã:" + orderId;
            String email = OrderDetailsRepository.getOrderDetailsByOrderId(orderId).getUser().getEmail();
            if(EmailRepository.sendEmail(subject, body, email)){
                return ResponseEntity.ok().build();
            }
            else return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
