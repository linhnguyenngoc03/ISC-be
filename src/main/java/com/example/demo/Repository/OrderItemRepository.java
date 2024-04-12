package com.example.demo.repository;

import com.example.demo.dbConnection.DBUtils;
import com.example.demo.model.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemRepository {

    //Get all item in order
    public static List<OrderItem> getAllOrderItem() throws Exception {
        List<OrderItem> orderItemList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from dbo.OrderItems";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setOrderItemId(table.getInt("orderItemsId"));
                        orderItem.setOrderId(table.getInt("orderId"));
                        orderItem.setProductId(table.getInt("productId"));
                        orderItem.setQuantity(table.getInt("quantity"));
                        //orderItem.setTotalPrice(table.getInt("totalPrice"));
                        orderItemList.add(orderItem);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return orderItemList;
    }

    //Get Order by Id
    public static OrderItem getOrderItemById(int orderItemId) throws Exception {
        OrderItem orderItem = new OrderItem();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from dbo.OrderItems where orderItemsId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, orderItemId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        orderItem.setOrderItemId(table.getInt("orderItemsId"));
                        orderItem.setOrderId(table.getInt("orderId"));
                        orderItem.setProductId(table.getInt("productId"));
                        orderItem.setQuantity(table.getInt("quantity"));
                        //orderItem.setTotalPrice(table.getInt("totalPrice"));
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return orderItem;
    }

    public static List<OrderItem> getOrderItemByOrderId(int orderId) throws Exception {
        List<OrderItem> orderItemList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from dbo.OrderItems where orderId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, orderId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setOrderId(table.getInt("orderItemsId"));
                        orderItem.setOrderId(table.getInt("orderId"));
                        orderItem.setProductId(table.getInt("productId"));
                        orderItem.setQuantity(table.getInt("quantity"));
                        //orderItem.setTotalPrice(table.getInt("totalPrice"));
                        orderItemList.add(orderItem);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return orderItemList;
    }

    //ko quan trọng
    public static List<OrderItem> getOrderItemByIdAndProductId(int orderId, int productId) throws Exception {
        List<OrderItem> orderItemList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from dbo.OrderItems where orderId = ? and productId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, orderId);
                pst.setInt(2, productId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setOrderId(table.getInt("orderItemsId"));
                        orderItem.setOrderId(table.getInt("orderId"));
                        orderItem.setProductId(table.getInt("productId"));
                        orderItem.setQuantity(table.getInt("quantity"));
                        //orderItem.setTotalPrice(table.getInt("totalPrice"));
                        orderItemList.add(orderItem);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return orderItemList;
    }

    //Delete existing Order item by product id
    public static boolean deleteOrderItem(int[] orderItemId) throws Exception {
        try {
            String sql = "Delete from dbo.OrderItems where orderItemsId = ?";
            Connection cn = DBUtils.makeConnection();
            int count = 0;
            if (cn != null) {
                for (int i = 0; i < orderItemId.length; i++) {
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, orderItemId[i]);
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

    public static boolean createOrderItem(OrderItem orderItem) throws Exception {
        try {
            if (getOrderItemByIdAndProductId(orderItem.getOrderId(), orderItem.getProductId()).size() == 0) {
                Connection cn = DBUtils.makeConnection();
                if (cn != null) {
                    String sql = "INSERT INTO OrderItems(orderId, productId, quantity)" +
                            "VALUES (?, ?, ?) ";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, orderItem.getOrderId());
                    pst.setInt(2, orderItem.getProductId());
                    pst.setInt(3, orderItem.getQuantity());
                    //pst.setInt(4, orderItem.getTotalPrice());
                    int row = pst.executeUpdate();
                    if (row > 0) return true;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //Update existing product by id
    public static boolean updateOrderItem(OrderItem orderItem) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Update dbo.OrderItems set quantity = ? where orderItemsId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, orderItem.getQuantity());
                //pst.setInt(2, orderItem.getTotalPrice());
                pst.setInt(2, orderItem.getOrderItemId());
                int row = pst.executeUpdate();
                if (row > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
