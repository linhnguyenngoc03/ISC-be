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
public class Address {
    private int addressId;
    private int userId;
    private String address;
    private Date dateCreate;
    private Date dateUpdate;

    public Address (int userId, String address){
        this.address = address;
        this.userId = userId;
    }
}
