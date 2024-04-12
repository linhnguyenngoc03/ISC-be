package com.example.demo.controller;

import com.example.demo.repository.AddressRepository;
import com.example.demo.model.Address;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/address")
public class AddressController {
    @GetMapping("/allAddress")
    public ResponseEntity<Object> getAllAddress() throws Exception {
        List<Address> addressList = AddressRepository.getAllAddress();
        if(addressList.size()>0) return ResponseEntity.ok().body(addressList);
        else return ResponseEntity.badRequest().build();

    }

    @GetMapping("/getAddressByUserUid")
    public ResponseEntity<Object> getAddressByUserUid(@RequestParam String userUid) throws Exception {
        List<Address> addressList = AddressRepository.getAddressByUserUid(userUid);
        if(addressList.size()>0) return ResponseEntity.ok().body(addressList);
        else return ResponseEntity.badRequest().build();
    }


    @GetMapping("/getAddressById")
    public ResponseEntity<Object> getAddressById(@RequestParam int addressId) throws Exception {
        Address address = AddressRepository.getAddressById(addressId);
        if(address.getAddressId() != 0) return ResponseEntity.ok().body(address);
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/createAddress")
    public ResponseEntity<Object> createAddress(@RequestBody Address address) throws Exception {
        if (AddressRepository.createAddress(address)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/deleteAddress")
    public ResponseEntity<Object> deleteAddress(@RequestParam int[] addressId) throws Exception {
        if (AddressRepository.deleteAddress(addressId)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/updateAddress")
    public ResponseEntity<Object> updateAddress(@RequestBody Address address) throws Exception {
        if (AddressRepository.updateAddress(address)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }
}
