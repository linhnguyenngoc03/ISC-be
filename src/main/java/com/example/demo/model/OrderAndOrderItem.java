package com.example.demo.model;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderAndOrderItem {
    private int orderId;
    private User user;
    private Payment payment;
    private Date orderDate;
    private Delivery delivery;
    private OrderStatus orderStatus;
    private String note;
    private int totalPayment;
    private Date paymentDate;
    private List<ProductAndOrderItem> productAndOrderItemList;
}
