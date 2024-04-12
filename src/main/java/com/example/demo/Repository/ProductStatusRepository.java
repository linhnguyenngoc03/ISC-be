package com.example.demo.repository;

import com.example.demo.dbConnection.DBUtils;
import com.example.demo.model.Product;
import com.example.demo.model.ProductStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductStatusRepository {
    public static List<ProductStatus> getAllProductStatus() throws Exception {
        List<ProductStatus> productStatusList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from dbo.ProductStatus";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        ProductStatus productStatus = new ProductStatus();
                        productStatus.setStatusId(table.getInt("statusId"));
                        productStatus.setStatus(table.getString("status"));
                        productStatusList.add(productStatus);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return productStatusList;
    }

    public static ProductStatus getProductStatusById(int statusId) throws Exception {
        ProductStatus productStatus = new ProductStatus();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from dbo.ProductStatus where statusId = ? ";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, statusId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        productStatus.setStatusId(table.getInt("statusId"));
                        productStatus.setStatus(table.getString("status"));
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return productStatus;
    }

    //Create new Category
    public static boolean createProductStatus(ProductStatus productStatus) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "INSERT INTO ProductStatus (status) VALUES (?)";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, productStatus.getStatus());
                int row = pst.executeUpdate();
                if (row > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //Update Category
    public static boolean updateProductStatus(ProductStatus productStatus) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Update dbo.ProductStatus Set categoryName = ? WHERE categoryId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, productStatus.getStatus());
                pst.setInt(2, productStatus.getStatusId());
                int row = pst.executeUpdate();
                if (row > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //Delete Category
    public static boolean deleteProductStatus(int[] statusId) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            int count = 0;
            if (cn != null) {
                for (int i = 0; i < statusId.length; i++) {
                    String sql = "Delete from dbo.ProductStatus where statusId = ?";
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
}
