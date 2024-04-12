package com.example.demo.repository;

import com.example.demo.dbConnection.DBUtils;
import com.example.demo.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public static List<User> getAllUser() throws Exception {
        List<User> userList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "select * from dbo.Users";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        User user = new User();
                        user.setUserId(table.getInt("userId"));
                        user.setUserRole(table.getInt("userRole"));
                        user.setUserName(table.getString("userName"));
                        user.setUserUid(table.getString("userUid"));
                        user.setEmail(table.getString("email"));
                        user.setPhoneNumber(table.getString("phoneNumber"));
                        user.setNote(table.getString("note"));
                        userList.add(user);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return userList;
    }

    public static User getUserByUserId(int userId) throws Exception {
        User user = new User();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "select * from dbo.Users where userId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, userId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        user.setUserId(table.getInt("userId"));
                        user.setUserRole(table.getInt("userRole"));
                        user.setUserName(table.getString("userName"));
                        user.setUserUid(table.getString("userUid"));
                        user.setEmail(table.getString("email"));
                        user.setPhoneNumber(table.getString("phoneNumber"));
                        user.setNote(table.getString("note"));
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    public static User getUserByUserUid(String userUid) throws Exception {
        User user = new User();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "select * from dbo.Users where userUid = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, userUid);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        user.setUserId(table.getInt("userId"));
                        user.setUserRole(table.getInt("userRole"));
                        user.setUserName(table.getString("userName"));
                        user.setUserUid(table.getString("userUid"));
                        user.setEmail(table.getString("email"));
                        user.setPhoneNumber(table.getString("phoneNumber"));
                        user.setNote(table.getString("note"));
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    public static boolean createUser(User user) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "INSERT INTO Users (userRole, userName, userUid, email, phoneNumber, note)"
                        + "VALUES(0, ?, ?, ?, ?, ?)";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, user.getUserName());
                pst.setString(2, user.getUserUid());
                pst.setString(3, user.getEmail());
                pst.setString(4, user.getPhoneNumber());
                pst.setString(5, user.getNote());
                int row = pst.executeUpdate();
                if (row > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateUser(User user) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "update Users set userName = ?, email = ?, phoneNumber = ?, note = ? WHERE userId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, user.getUserName());
                pst.setString(2, user.getEmail());
                pst.setString(3, user.getPhoneNumber());
                pst.setString(4, user.getNote());
                pst.setInt(5, user.getUserId());
                int row = pst.executeUpdate();
                if (row > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }


    public static boolean deleteUser(int[] userId) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            int count = 0;
            if (cn != null) {
                for (int i = 0; i < userId.length; i++) {
                    String sql = "Delete from Users where userId = ?";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, userId[i]);
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
}
