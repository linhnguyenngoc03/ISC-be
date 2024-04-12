package com.example.demo.model;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Order {
    private int orderId;
    private int userId;
    private int paymentId;
    private Date orderDate;
    private int deliveryId;
    private int statusId;
    private String note;
    private int totalPayment;
    private Date paymentDate;

    public Order(int userId, int paymentId, int deliveryId, int statusId, String note, int totalPayment){
        this.userId = userId;
        this.paymentId = paymentId;
        this.deliveryId = deliveryId;
        this.statusId = statusId;
        this.note = note;
        this.totalPayment = totalPayment;
    }
}
