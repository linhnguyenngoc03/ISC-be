package com.example.demo.repository;

import com.example.demo.dbConnection.DBUtils;
import com.example.demo.model.*;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    public static List<Order> getAllOrder() throws Exception {
        List<Order> orderList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from dbo.Orders";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        Order order = new Order();
                        order.setOrderId(table.getInt("orderId"));
                        order.setUserId(table.getInt("userId"));
                        order.setPaymentId(table.getInt("paymentId"));
                        order.setOrderDate(table.getDate("orderDate"));
                        order.setDeliveryId(table.getInt("deliveryId"));
                        order.setStatusId(table.getInt("statusId"));
                        order.setNote(table.getString("note"));
                        order.setTotalPayment(table.getInt("totalPayment"));
                        order.setPaymentDate(table.getDate("paymentDate"));
                        orderList.add(order);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return orderList;
    }

    //Get Order by Id
    public static Order getOrderById(int orderId) throws Exception {
        Order order = new Order();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from dbo.Orders where orderId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, orderId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        order.setOrderId(table.getInt("orderId"));
                        order.setUserId(table.getInt("userId"));
                        order.setPaymentId(table.getInt("paymentId"));
                        order.setOrderDate(table.getDate("orderDate"));
                        order.setDeliveryId(table.getInt("deliveryId"));
                        order.setStatusId(table.getInt("statusId"));
                        order.setNote(table.getString("note"));
                        order.setTotalPayment(table.getInt("totalPayment"));
                        order.setPaymentDate(table.getDate("paymentDate"));
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return order;
    }

    //Delete existing Order by id
    public static boolean deleteOrder(int[] orderId) throws Exception {
        try{
            String sql = "Delete from dbo.Orders where orderId = ?";
            Connection cn = DBUtils.makeConnection();
            int count = 0;
            if (cn != null) {
                for (int i = 0; i<orderId.length; i++) {
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, orderId[i]);
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

    public static boolean createOrder(Order order) throws Exception {
        try{
            Connection cn = DBUtils.makeConnection();
            String sql;
            if (cn != null) {
                if(order.getPaymentId() == 1) {
                    sql = "INSERT INTO Orders(userId, paymentId, orderDate, deliveryId, statusId, note, totalPayment, paymentDate)" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
                }
                else{
                    sql ="INSERT INTO Orders(userId, paymentId, orderDate, deliveryId, statusId, note, totalPayment)" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?) ";
                }
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, order.getUserId());
                pst.setInt(2, order.getPaymentId());
                pst.setString(3, DBUtils.getCurrentDate());
                pst.setInt(4, order.getDeliveryId());
                pst.setInt(5, order.getStatusId());
                pst.setString(6, order.getNote());
                pst.setInt(7, order.getTotalPayment());

                if(order.getPaymentId() == 1) pst.setString(8, DBUtils.getCurrentDate());
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

    //Update existing product by id
    public static boolean updateOrder(Order Order) throws Exception {
        try{
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Update dbo.Orders set paymentId = ?, deliveryId = ?, statusId = ?, note = ?, totalPayment = ?, paymentDate = ? where OrderId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, Order.getPaymentId());
                pst.setInt(2, Order.getDeliveryId());
                pst.setInt(3, Order.getStatusId());
                pst.setString(4, Order.getNote());
                pst.setInt(5, Order.getTotalPayment());

                if(Order.getPaymentDate() != null) {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    String strDate = dateFormat.format(Order.getPaymentDate());
                    pst.setString(6, strDate);
                }else pst.setString(6, "");

                pst.setInt(7, Order.getOrderId());
                int row = pst.executeUpdate();
                if (row > 0) return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static List<ProductAndOrderItem> getProductAndOrderItem(int orderId) throws Exception {
        List<ProductAndOrderItem> productAndOrderItemList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "select o.orderItemsId, o.quantity, o.productId, p.productName, p.price, p.quantity as productQuantity, p.categoryId, p.description, p.status, p.image, p.dateCreate, p.dateUpdate " +
                        "from OrderItems o " +
                        "left join Product p on p.productId = o.productId " +
                        "where o.orderId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, orderId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        ProductAndOrderItem productAndOrderItem = new ProductAndOrderItem();
                        productAndOrderItem.setOrderItemId(table.getInt("orderItemsId"));
                        productAndOrderItem.setQuantity(table.getInt("quantity"));

                        Product product = new Product();
                        product.setProductId(table.getInt("productId"));
                        product.setProductName(table.getString("productName"));
                        product.setPrice(table.getInt("price"));
                        product.setQuantity(table.getInt("productQuantity"));
                        product.setCategoryId(table.getInt("categoryId"));
                        product.setStatus(table.getInt("status"));
                        product.setDescription(table.getString("description"));
                        product.setImage(table.getString("image"));
                        product.setDateCreate(table.getDate("dateCreate"));
                        product.setDateUpdate(table.getDate("dateUpdate"));

                        productAndOrderItem.setProduct(product);
                        productAndOrderItemList.add(productAndOrderItem);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return productAndOrderItemList;
    }

    public static List<Order> getOrderByUserId(int userId) throws Exception {
        List<Order> orderList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from dbo.Orders where userId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, userId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        Order order = new Order();
                        order.setOrderId(table.getInt("orderId"));
                        order.setUserId(table.getInt("userId"));
                        order.setPaymentId(table.getInt("paymentId"));
                        order.setOrderDate(table.getDate("orderDate"));
                        order.setDeliveryId(table.getInt("deliveryId"));
                        order.setStatusId(table.getInt("statusId"));
                        order.setNote(table.getString("note"));
                        order.setTotalPayment(table.getInt("totalPayment"));
                        order.setPaymentDate(table.getDate("paymentDate"));
                        orderList.add(order);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return orderList;
    }

    public static boolean cancelOrder(int orderId){
        try {
            Order order = getOrderById(orderId);
            if (order.getStatusId() == 1){
                order.setStatusId(5);
                if(updateOrder(order)) {
                    List<OrderItem> orderItemList = OrderItemRepository.getOrderItemByOrderId(orderId);
                    for (int i = 0; i < orderItemList.size(); i++) {
                        int productId = orderItemList.get(i).getProductId();
                        Product product = ProductRepository.getProductById(productId);
                        int newQuantity = orderItemList.get(i).getQuantity() + product.getQuantity();
                        product.setQuantity(newQuantity);
                        ProductRepository.updateProduct(product);
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateOrderStatus(Order Order) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Update dbo.Orders set statusId = ? where OrderId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, Order.getStatusId());
                pst.setInt(2, Order.getOrderId());
                pst.executeUpdate();
                int row = pst.executeUpdate();

                if (row > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
