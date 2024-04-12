package com.example.demo.repository;

import com.example.demo.dbConnection.DBUtils;
import com.example.demo.model.Category;
import com.example.demo.model.CategoryAndProduct;
import com.example.demo.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    public static List<Category> getAllCategory() throws Exception {
        List<Category> categoryList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from dbo.Category";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        Category category = new Category();
                        category.setCategoryId(table.getInt("categoryId"));
                        category.setCategoryName(table.getString("categoryName"));
                        categoryList.add(category);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return categoryList;
    }

    public static Category getCategoryById(int categoryId) throws Exception {
        Category category = new Category();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from dbo.Category where categoryId = ? ";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, categoryId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        category.setCategoryId(table.getInt("categoryId"));
                        category.setCategoryName(table.getString("categoryName"));
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return category;
    }

    //Create new Category
    public static boolean createCategory(Category category) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "INSERT INTO Category (categoryName) VALUES (?)";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, category.getCategoryName());
                int row = pst.executeUpdate();
                if (row > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //Update Category
    public static boolean updateCategory(Category category) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Update dbo.Category Set categoryName = ? WHERE categoryId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, category.getCategoryName());
                pst.setInt(2, category.getCategoryId());
                int row = pst.executeUpdate();
                if (row > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //Delete Category
    public static boolean deleteCategory(int[] categoryId) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            int count = 0;
            if (cn != null) {
                for (int i = 0; i < categoryId.length; i++) {
                    String sql = "Delete from dbo.Category where categoryId = ?";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, categoryId[i]);
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


    //Get both Category and Product
    public static List<CategoryAndProduct> getCategoryAndProduct() throws Exception {
        List<CategoryAndProduct> categoryAndProductList = new ArrayList<>();
        List<Category> categoryList = getAllCategory();
        try {
            for (int i = 0; i < categoryList.size(); i++) {
                int categoryId = categoryList.get(i).getCategoryId();
                String categoryName = categoryList.get(i).getCategoryName();
                String sql = "select * from dbo.Product where categoryId = '" + categoryId + "'";
                List<Product> productList = new ArrayList<>();

                Connection cn = DBUtils.makeConnection();
                if (cn != null) {
                    PreparedStatement pst = cn.prepareStatement(sql);
                    ResultSet table = pst.executeQuery();
                    if (table != null) {
                        while (table.next()) {
                            Product product = new Product();
                            product.setProductId(table.getInt("productId"));
                            product.setProductName(table.getString("productName"));
                            product.setPrice(table.getInt("price"));
                            product.setQuantity(table.getInt("quantity"));
                            product.setCategoryId(table.getInt("categoryId"));
                            product.setStatus(table.getInt("status"));
                            product.setDescription(table.getString("description"));
                            product.setImage(table.getString("image"));
                            product.setDateCreate(table.getDate("dateCreate"));
                            product.setDateUpdate(table.getDate("dateUpdate"));
                            productList.add(product);
                        }
                    }
                }
                CategoryAndProduct categoryAndProduct = new CategoryAndProduct();
                categoryAndProduct.setCategoryId(categoryId);
                categoryAndProduct.setCategoryName(categoryName);
                categoryAndProduct.setProductList(productList);
                categoryAndProductList.add(categoryAndProduct);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return categoryAndProductList;
    }
}
