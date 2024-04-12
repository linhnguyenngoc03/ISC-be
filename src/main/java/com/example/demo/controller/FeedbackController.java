package com.example.demo.controller;

import com.example.demo.repository.FeedbackRepository;
import com.example.demo.model.Feedback;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    @GetMapping("/allFeedback")
    public ResponseEntity<Object> getAllFeedback() throws Exception {
        List<Feedback> feedbackList = FeedbackRepository.getAllFeedback();
        if(feedbackList.size()>0) return ResponseEntity.ok().body(feedbackList);
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getFeedbackById")
    public ResponseEntity<Object> getFeedbackById(@RequestParam int feedbackId) throws Exception {
        Feedback feedback = FeedbackRepository.getFeedbackById(feedbackId);
        if(feedback.getFeedbackId() != 0) return ResponseEntity.ok().body(feedback);
        else return ResponseEntity.badRequest().build();
    }

    @PostMapping("/createFeedback")
    public ResponseEntity<Object> createFeedback(@RequestBody Feedback feedback) throws Exception {
        if(FeedbackRepository.createFeedback(feedback)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/deleteFeedback")
    public ResponseEntity<Object> deleteFeedback(@RequestParam int[] feedbackId) throws Exception {
        if(FeedbackRepository.deleteFeedback(feedbackId)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/updateFeedback")
    public ResponseEntity<Object> updateFeedback(@RequestBody Feedback feedback) throws Exception {
        if(FeedbackRepository.updateFeedback(feedback)) return ResponseEntity.ok().build();
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getFeedbackByProductId")
    public ResponseEntity<Object> getFeedbackByProductId(@RequestParam int productId) throws Exception {
        List<Feedback> feedbackList = FeedbackRepository.getFeedbackByProductId(productId);
        if(feedbackList.size()>0) return ResponseEntity.ok().body(feedbackList);
        else return ResponseEntity.badRequest().build();
    }
}
