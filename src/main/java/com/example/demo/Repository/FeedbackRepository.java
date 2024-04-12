package com.example.demo.repository;

import com.example.demo.dbConnection.DBUtils;
import com.example.demo.model.Feedback;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackRepository {
    //Get all feedback
    public static List<Feedback> getAllFeedback() throws Exception {
        List<Feedback> feedbackList = new ArrayList<>();
        try{
            String sql = "select * from dbo.Feedback";
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        Feedback feedback = new Feedback();
                        feedback.setFeedbackId(table.getInt("feedbackId"));
                        feedback.setUser(UserRepository.getUserByUserId(table.getInt("userId")));
                        feedback.setProduct(ProductRepository.getProductById(table.getInt("productId")));
                        feedback.setContent(table.getString("content"));
                        feedback.setDate(table.getDate("date"));
                        feedbackList.add(feedback);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return feedbackList;
    }

    //Get feedback by id
    public static Feedback getFeedbackById(int feedbackId) throws Exception {
        Feedback feedback = new Feedback();
        try {
            String sql = "select * from dbo.Feedback where feedbackId = ?";
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, feedbackId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        feedback.setFeedbackId(table.getInt("feedbackId"));
                        feedback.setUser(UserRepository.getUserByUserId(table.getInt("userId")));
                        feedback.setProduct(ProductRepository.getProductById(table.getInt("productId")));
                        feedback.setContent(table.getString("content"));
                        feedback.setDate(table.getDate("date"));
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return feedback;
    }

    //Add new feedback
    public static boolean createFeedback(Feedback feedback) throws Exception {
        String date = DBUtils.getCurrentDate();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SET ANSI_WARNINGS OFF;" +
                        "INSERT INTO Feedback(userId, productId, content, date)" +
                        "VALUES (?, ?, ?, ?) " +
                        "SET ANSI_WARNINGS ON";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, feedback.getUser().getUserId());
                System.out.print(feedback.getUser().getUserId());
                pst.setInt(2, feedback.getProduct().getProductId());
                System.out.print(feedback.getProduct().getProductId());
                pst.setString(3, feedback.getContent());
                System.out.print(feedback.getContent());
                pst.setString(4, date);

                int row = pst.executeUpdate();
                if (row > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //Delete existing feedback by id
    public static boolean deleteFeedback(int[] feedbackId) throws Exception {
        try{
            String sql = "Delete from dbo.Feedback where feedbackId = ?";
            Connection cn = DBUtils.makeConnection();
            int count = 0;
            if (cn != null) {
                for (int i = 0; i < feedbackId.length; i++) {
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, feedbackId[i]);
                    int row = pst.executeUpdate();
                    if (row > 0) count++;
                }
                if (count > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //Update existing feedback by id
    public static boolean updateFeedback(Feedback feedback) throws Exception {
        String dateUpdate = DBUtils.getCurrentDate();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Update dbo.Feedback set content = ? where feedbackId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, feedback.getContent());
                pst.setInt(2, feedback.getFeedbackId());
                int row = pst.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static List<Feedback> getFeedbackByProductId(int productId) throws Exception {
        List<Feedback> feedbackList = new ArrayList<>();
        try {
            String sql = "select * from dbo.Feedback where productId = ?";
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, productId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        Feedback feedback = new Feedback();
                        feedback.setFeedbackId(table.getInt("feedbackId"));
                        feedback.setUser(UserRepository.getUserByUserId(table.getInt("userId")));
                        feedback.setProduct(ProductRepository.getProductById(table.getInt("productId")));
                        feedback.setContent(table.getString("content"));
                        feedback.setDate(table.getDate("date"));
                        feedbackList.add(feedback);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return feedbackList;
    }
}
