package com.example.demo.controller;

import com.example.demo.repository.DeliveryRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.model.OrderItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderItem")
public class OrderItemController {
    @GetMapping("/getAllOrderItem")
    public ResponseEntity<Object> getAllOrderItem() throws Exception {
        List<OrderItem> orderList = OrderItemRepository.getAllOrderItem();
        if(orderList.size()>0) return ResponseEntity.ok().body(orderList);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getOrderItemById")
    public ResponseEntity<Object> getOrderItemById(int orderItemId) throws Exception {
        OrderItem orderItem = OrderItemRepository.getOrderItemById(orderItemId);
        if(orderItem.getOrderId() != 0) return ResponseEntity.ok().body(orderItem);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getOrderItemByOrderId")
    public ResponseEntity<Object> getOrderItemByOrderId(int orderId) throws Exception {
        List<OrderItem> orderList = OrderItemRepository.getOrderItemByOrderId(orderId);
        if(orderList.size()>0) return ResponseEntity.ok().body(orderList);
        else return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/deleteOrderItem")
    public ResponseEntity<Object> deleteOrderItem(@RequestParam int[] orderItemId) throws Exception {
        if(OrderItemRepository.deleteOrderItem(orderItemId)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/createOrderItem")
    public ResponseEntity<Object> createOrderItem(@RequestBody OrderItem orderItem) throws Exception {
        if(OrderItemRepository.createOrderItem(orderItem)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/updateOrderItem")
    public ResponseEntity<Object> updateOrderItem(@RequestBody OrderItem orderItem) throws Exception {
        if(OrderItemRepository.updateOrderItem(orderItem)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }
}
