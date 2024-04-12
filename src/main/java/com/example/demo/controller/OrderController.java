package com.example.demo.controller;

import com.example.demo.repository.*;
import com.example.demo.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @GetMapping("/getAllOrder")
    public ResponseEntity<Object> getAllOrder() throws Exception {
        List<Order> orderList = OrderRepository.getAllOrder();
        if(orderList.size()>0) return ResponseEntity.ok().body(orderList);
        else return ResponseEntity.badRequest().build();
    }

    /*@GetMapping("/getOrderById")
    public ResponseEntity<Object> getOrderById(int orderId) throws Exception {
        Order order = OrderRepository.getOrderById(orderId);
        if(order != null) return ResponseEntity.ok().body(order);
        else return ResponseEntity.badRequest().build();
    }*/

    @GetMapping("/getOrderById")
    public ResponseEntity<Object> getOrderById(int orderId) throws Exception {
        OrderAndOrderItem orderAndOrderItem = OrderDetailsRepository.getOrderDetailsByOrderId(orderId);
        if(orderAndOrderItem.getOrderId() != 0) return ResponseEntity.ok().body(orderAndOrderItem);
        else return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/deleteOrder")
    public ResponseEntity<Object> deleteOrder(@RequestParam int[] orderId) throws Exception {
        if(OrderRepository.deleteOrder(orderId)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/createOrder")
    public ResponseEntity<Object> createOrder(@RequestBody Order order) throws Exception {
        if(OrderRepository.createOrder(order)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/updateOrder")
    public ResponseEntity<Object> updateOrder(@RequestBody Order order) throws Exception {
        if(OrderRepository.updateOrder(order)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/updateOrderStatus")
    public ResponseEntity<Object> updateOrderStatus(@RequestBody Order order) throws Exception {
        System.out.println(order.toString());
        if(OrderRepository.updateOrderStatus(order)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/makeOrder")
    public ResponseEntity<Object> makeOrder(@RequestBody OrderDetails orderDetails) throws Exception {
        int orderId = OrderDetailsRepository.createOrderDetails(orderDetails);
        if(orderId > 0) {
            String body = EmailRepository.messageCreate(orderId);
            String subject = "Xác nhận đơn hàng mã:" + orderId;
            String email = OrderDetailsRepository.getOrderDetailsByOrderId(orderId).getUser().getEmail();
            EmailRepository.sendEmail(subject, body, email);
            return ResponseEntity.ok().body(orderId);
        }
        else return ResponseEntity.badRequest().body("Đã xa ra lỗi!");
    }
    @GetMapping("/getAllOrderAndOrderItem")
    public ResponseEntity<Object> getOrderAndOrderItem() throws Exception {
        List<OrderAndOrderItem> orderAndOrderItemList = OrderDetailsRepository.getAllOrderDetails();
        if(orderAndOrderItemList.size()>0) return ResponseEntity.ok().body(orderAndOrderItemList);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getOrderAndOrderItemByUserId")
    public ResponseEntity<Object> getOrderDetailsByUserId(@RequestParam int userId) throws Exception {
        List<OrderAndOrderItem> orderAndOrderItemList = OrderDetailsRepository.getOrderDetailsByUserId(userId);
        if(orderAndOrderItemList.size()>0) return ResponseEntity.ok().body(orderAndOrderItemList);
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/cancelOrder")
    public ResponseEntity<Object> cancelOrder(@RequestParam int orderId) throws Exception {
        if(OrderRepository.cancelOrder(orderId)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }
}
