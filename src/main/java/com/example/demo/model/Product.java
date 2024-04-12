package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    private int productId;
    private String productName;
    private int price;
    private int quantity;
    private int categoryId;
    private int status;
    private String description;
    private String image;
    private Date dateCreate;
    private Date dateUpdate;
}
