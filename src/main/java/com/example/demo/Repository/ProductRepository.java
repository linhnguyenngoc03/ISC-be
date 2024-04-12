package com.example.demo.repository;

import com.example.demo.dbConnection.DBUtils;
import com.example.demo.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductRepository {
    public static List<Product> getProduct(String sql) throws Exception {
        List<Product> productList = new ArrayList<>();
        try {
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
        }catch (SQLException e){
            e.printStackTrace();
        }
        return productList;
    }

    //Get all product
    public static List<Product> getAllProduct() throws Exception {
        List<Product> productList = getProduct("select * from dbo.Product");
        return productList;
    }

    //Chuyển tiếng Việt ko dấu
    public static String unAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replace("đ", "d");
    }

    //Search product by it's name
    public static List<Product> searchByName(String searchValue) throws Exception {
        /*String sql = "select * from dbo.Product where productName like N'%" + searchValue + "%'";
        List<Product> productList  = getProduct(sql);
        return productList;*/

        //Search chữ ko dấu
        List<Product> productList = getProduct("select * from dbo.Product");
        Product product = new Product();
        List<Product> result = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++){
            product = productList.get(i);
            if(unAccent(product.getProductName()).toLowerCase().contains(unAccent(searchValue).toLowerCase())){
                result.add(product);
            }
        }
        return result;
    }

    //Filter product by id
    public static Product getProductById(int productId) throws Exception {
        String sql = "select * from dbo.Product where productId = '" + productId + "'";
        Product product = getProduct(sql).get(0);
        return product;
    }

    public static int getCategoryId(String categoryName) throws Exception {
        List<Product> productList = new ArrayList<>();
        int categoryId = 0;
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from dbo.Category where categoryName = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, categoryName);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        categoryId = table.getInt("categoryId");
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return categoryId;
    }

    //Multiple filter
    public static List<Product> multiFilter(String categoryName, String price, String status) throws Exception {
        int categoryId = getCategoryId(categoryName);

        String sql = "Select * from dbo.Product where";

        if (categoryName!=null) sql = sql + " categoryId = " + categoryId + " and ";

        if(price!=null) {
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(price);
            int from = -1, to = -1;
            if (matcher.find()) {
                from = Integer.parseInt(matcher.group());
                if (matcher.find()) {
                    to = Integer.parseInt(matcher.group());
                }
            }
            if(to != -1){
                sql = sql + " price >= " + from + " and price < "+ to + " and ";
            } else {
                sql = sql + " price >= " + from + " and ";
            }

        }

        if (status!=null) sql = sql + " status =  + status + ";
        int lenght = sql.length();
        if (sql.substring(lenght-5,lenght-1).trim().equals("and")) sql = sql.substring(0, lenght-5);
        List<Product> productList = getProduct(sql);
        return productList;
    }

    //Filter product by it's category
    //Đã tích hợp trong multiFilter
    public static List<Product> filterByCategoryId(int categoryId) throws Exception {
        String sql = "select * from dbo.Product where categoryId = " + categoryId;
        List<Product> productList = getProduct(sql);
        return productList;
    }

    //Filter product by price from x to y
    //Đã tích hợp trong multiFilter
    public static List<Product> sortByPrice(int from, int to) throws Exception {
        String sql = "Select * from dbo.Product where price >= " + from + " and price <= " + to;
        List<Product> productList = getProduct(sql);
        return productList;
    }

    //Filter product by status
    //Đã tích hợp trong multiFilter
    public static List<Product> filterByStatus(String status) throws Exception {
        String sql = "Select * from dbo.Product where status = N'" + status + "'";
        List<Product> productList = getProduct(sql);
        return productList;
    }

    //Add new product
    public static boolean createProduct(Product product) throws Exception {
        String dateCreate = DBUtils.getCurrentDate();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SET ANSI_WARNINGS OFF;" +
                        "INSERT INTO Product(productName, price, quantity, categoryId, status, description, image, dateCreate) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                        "SET ANSI_WARNINGS ON";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, product.getProductName());
                pst.setInt(2, product.getPrice());
                pst.setInt(3, product.getQuantity());
                pst.setInt(4, product.getCategoryId());
                pst.setInt(5, 1);
                pst.setString(6, product.getDescription());
                pst.setString(7, product.getImage());
                pst.setString(8, dateCreate);
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

    //Delete existing product by id
    public static boolean deleteProduct(int[] productId) throws Exception {
        String sql = "Delete from dbo.Product where productId = ?";
        try {
            Connection cn = DBUtils.makeConnection();
            int count = 0;
            if (cn != null) {
                for (int i = 0; i < productId.length; i++) {
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, productId[i]);
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

    //Delete product by changing product status to "xóa"
    public static boolean deleteProductByChangingStatus(int[] productId) throws Exception {
        String sql = "Update dbo.Product set status = 4 where productId = ?";
        try {
            Connection cn = DBUtils.makeConnection();
            int count = 0;
            if (cn != null) {
                for (int i = 0; i < productId.length; i++) {
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, productId[i]);
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

    //Update existing product by id
    public static boolean updateProduct(Product product) throws Exception {
        String dateUpdate = DBUtils.getCurrentDate();
        try {
            int status = product.getStatus();
            if(status != 4 && product.getQuantity() == 0){
                status = 3;
            }
            if (status >= 0) {
                Connection cn = DBUtils.makeConnection();
                if (cn != null) {
                    String sql = "Update dbo.Product set productName = ?, price = ?, quantity = ?, categoryId = ?, status = ?, description = ?, image = ?, dateUpdate = ? where productId = ?";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setString(1, product.getProductName());
                    pst.setInt(2, product.getPrice());
                    pst.setInt(3, product.getQuantity());
                    pst.setInt(4, product.getCategoryId());
                    pst.setInt(5, status);
                    pst.setString(6, product.getDescription());
                    pst.setString(7, product.getImage());
                    pst.setString(8, dateUpdate);
                    pst.setInt(9, product.getProductId());
                    int row = pst.executeUpdate();
                    if (row > 0) {
                        return true;
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static String[] getProductName() throws Exception {
        String[] productName = null;
        try {
            List<Product> productList = getProduct("select * from dbo.Product");
            productName = new String[productList.size()];
            for (int i = 0; i < productList.size(); i++){
                productName[i] = productList.get(i).getProductName();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return productName;
    }
}
