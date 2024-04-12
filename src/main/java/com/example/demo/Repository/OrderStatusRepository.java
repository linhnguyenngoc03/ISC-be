package com.example.demo.repository;

import com.example.demo.dbConnection.DBUtils;
import com.example.demo.model.OrderStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderStatusRepository {
    public static List<OrderStatus> getAllOrderStatus() throws Exception {
        List<OrderStatus> orderStatusList = new ArrayList<>();
        try{
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from OrderStatus";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        OrderStatus orderStatus = new OrderStatus();
                        orderStatus.setStatusId(table.getInt("statusId"));
                        orderStatus.setStatus(table.getString("status"));
                        orderStatusList.add(orderStatus);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return orderStatusList;
    }

    public static OrderStatus getOrderStatusById(int statusId) throws Exception {
        OrderStatus orderStatus = new OrderStatus();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from OrderStatus where statusId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, statusId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        orderStatus.setStatusId(table.getInt("statusId"));
                        orderStatus.setStatus(table.getString("status"));
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return orderStatus;
    }

    public static boolean createOrderStatus(OrderStatus orderStatus) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "INSERT INTO OrderStatus(status) Values (?)";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, orderStatus.getStatus());
                int row = pst.executeUpdate();
                if (row > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteOrderStatus(int[] statusId) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            int count = 0;
            if (cn != null) {
                for (int i = 0; i < statusId.length; i++) {
                    String sql = "Delete from OrderStatus where statusId = ?";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, statusId[i]);
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

    public static boolean updateOrderStatus(OrderStatus orderStatus) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "UPDATE OrderStatus SET status = ? WHERE statusId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, orderStatus.getStatusId());
                pst.setString(2, orderStatus.getStatus());
                int row = pst.executeUpdate();
                if (row > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
