package com.example.demo.controller;
import com.example.demo.repository.DeliveryRepository;
import com.example.demo.model.Delivery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {

    @GetMapping("/getDeliveryById")
    public ResponseEntity<Object> getDeliveryByDeliveryId (@RequestParam int deliveryId) throws Exception {
        Delivery delivery = DeliveryRepository.getDeliveryById(deliveryId);
        if (delivery.getDeliveryId() != 0) return ResponseEntity.ok().body(delivery);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getAllDelivery")
    public ResponseEntity<Object> getAllDelivery() throws Exception {
        List<Delivery> deliveryList = DeliveryRepository.getAllDelivery();
        if(deliveryList.size()>0) return ResponseEntity.ok().body(deliveryList);
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/createDelivery")
    public ResponseEntity<Object> createDelivery(@RequestBody Delivery delivery) throws Exception {
        if(DeliveryRepository.createDelivery(delivery)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/updateDelivery")
    public ResponseEntity<Object> updateDelivery(@RequestBody Delivery delivery) throws Exception {
        if(DeliveryRepository.updateDelivery(delivery)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/deleteDelivery")
    public ResponseEntity<Object> deleteDelivery(@RequestParam int[] deliveryId) throws Exception {
        if(DeliveryRepository.deleteDelivery(deliveryId)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }
}
