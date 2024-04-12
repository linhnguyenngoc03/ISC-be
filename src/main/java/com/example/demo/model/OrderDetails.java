package com.example.demo.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetails {
    private String userUid;
    private int paymentId;
    private int deliveryAddressId;
    private String note;
    private int totalPayment;
    private List<CartItems> cartItemsList;
}
