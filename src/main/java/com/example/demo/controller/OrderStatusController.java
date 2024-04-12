package com.example.demo.controller;


import com.example.demo.repository.OrderStatusRepository;
import com.example.demo.model.OrderStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/all/orderstatus")
public class OrderStatusController {
    @GetMapping("/allOrderStatus")
    public ResponseEntity<Object> getAllOrderStatus() throws Exception {
        List<OrderStatus> orderStatusList = OrderStatusRepository.getAllOrderStatus();
        if (orderStatusList.size() > 0) {
            return ResponseEntity.ok().body(orderStatusList);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/getOrderStatusById")
    public ResponseEntity<Object> getPaymentById(@RequestParam int statusId) throws Exception {
        OrderStatus orderStatus = OrderStatusRepository.getOrderStatusById(statusId);
        if (orderStatus.getStatusId() != 0) return ResponseEntity.ok().body(orderStatus);
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/createOrderStatus")
    public ResponseEntity<Object> createOrderStatus(@RequestBody OrderStatus orderStatus) throws Exception {
        if(OrderStatusRepository.createOrderStatus(orderStatus)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/updateOrderStatus")
    public ResponseEntity<Object> updateOrderStatus(@RequestBody OrderStatus orderStatus) throws Exception {
        if(OrderStatusRepository.updateOrderStatus(orderStatus)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/deletePayment")
    public ResponseEntity<Object> deleteOrderStatus(@RequestParam int[] statusId) throws Exception {
        if(OrderStatusRepository.deleteOrderStatus(statusId)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }
}
