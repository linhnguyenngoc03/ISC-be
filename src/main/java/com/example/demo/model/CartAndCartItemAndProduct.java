package com.example.demo.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartAndCartItemAndProduct {
    private Cart cart;
    private List<ProductAndCartItem> productAndCartItemList;
}
