package com.example.demo.repository;

import com.example.demo.dbConnection.DBUtils;
import com.example.demo.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsRepository {
    public static int createOrderDetails(OrderDetails orderDetails) throws Exception {
        try {
            List<CartItems> cartItemsList = orderDetails.getCartItemsList();

            //check product quantity
            for (int i = 0; i < cartItemsList.size(); i++) {
                CartItems cartItems = CartItemsRepository.getCartItemsById(cartItemsList.get(i).getCartItemId());
                int productId = cartItems.getProductId();
                int quantity = cartItems.getQuantity();
                Product product = ProductRepository.getProductById(productId);
                int newQuantity = product.getQuantity() - quantity;
                if (newQuantity < 0) return -1;
            }

            //create new delivery
            String address = AddressRepository.getAddressById(orderDetails.getDeliveryAddressId()).getAddress();
            Delivery delivery = new Delivery(0, address);
            DeliveryRepository.createDelivery(delivery);
            List<Delivery> deliveryList = DeliveryRepository.getAllDelivery();
            delivery = deliveryList.get(deliveryList.size() - 1);

            //create new order
            String userUid = orderDetails.getUserUid();
            int userId = UserRepository.getUserByUserUid(userUid).getUserId();
            int paymentId = orderDetails.getPaymentId();
            int deliveryId = delivery.getDeliveryId();
            int statusId = 1;
            String note = orderDetails.getNote();
            int totalPayment = orderDetails.getTotalPayment() + PaymentRepository.getPaymentById(orderDetails.getPaymentId()).getPaymentCost();
            Order order = new Order(userId, paymentId, deliveryId, statusId, note, totalPayment);
            OrderRepository.createOrder(order);
            List<Order> orderList = OrderRepository.getAllOrder();
            order = orderList.get(orderList.size() - 1);

            //create order items
            int orderId = order.getOrderId();
            int cartItemId[] = new int[cartItemsList.size()]; //create int cartItemId to delete cartItems
            for (int i = 0; i < cartItemsList.size(); i++) {
                CartItems cartItems = CartItemsRepository.getCartItemsById(cartItemsList.get(i).getCartItemId());
                cartItemId[i] = cartItems.getCartItemId(); //get cartItemId to delete cartItems
                int productId = cartItems.getProductId();
                int quantity = cartItems.getQuantity();
                OrderItem orderItem = new OrderItem(0, orderId, productId, quantity);
                OrderItemRepository.createOrderItem(orderItem);

                //update product quantity
                Product product = ProductRepository.getProductById(productId);
                int newQuantity = product.getQuantity() - quantity;
                product.setQuantity(newQuantity);
                ProductRepository.updateProduct(product);
            }
            CartItemsRepository.deleteCartItem(cartItemId);//delete cartItems
            return orderId;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    //General get to get Order Details
    public static List<OrderAndOrderItem> getOrderDetails(String sql) throws Exception {
        List<OrderAndOrderItem> orderAndOrderItemList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        OrderAndOrderItem orderAndOrderItem = new OrderAndOrderItem();
                        int orderId = table.getInt("orderId");
                        orderAndOrderItem.setOrderId(orderId);

                        //set User
                        User user = new User(table.getInt("userId"),
                                table.getInt("userRole"),
                                table.getString("userName"),
                                table.getString("userUid"),
                                table.getString("email"),
                                table.getString("phoneNumber"),
                                table.getString("note"));
                        orderAndOrderItem.setUser(user);

                        //set payment
                        Payment payment = new Payment(table.getInt("paymentId"),
                                table.getString("paymentType"), table.getInt("paymentCost"));
                        orderAndOrderItem.setPayment(payment);

                        //set order date
                        orderAndOrderItem.setOrderDate(table.getDate("orderDate"));

                        //set delivery
                        Delivery delivery = new Delivery(table.getInt("deliveryId"),
                                table.getString("address"));
                        orderAndOrderItem.setDelivery(delivery);

                        //set order status
                        OrderStatus orderStatus = new OrderStatus(table.getInt("statusId"),
                                table.getString("status"));
                        orderAndOrderItem.setOrderStatus(orderStatus);

                        orderAndOrderItem.setNote(table.getString("note"));
                        orderAndOrderItem.setTotalPayment(table.getInt("totalPayment"));
                        orderAndOrderItem.setPaymentDate(table.getDate("paymentDate"));
                        orderAndOrderItem.setOrderDate(table.getDate("orderDate"));

                        orderAndOrderItem.setProductAndOrderItemList(OrderRepository.getProductAndOrderItem(orderId));
                        orderAndOrderItemList.add(orderAndOrderItem);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return orderAndOrderItemList;
    }

    /*public static List<OrderAndOrderItem> getAllOrderDetails() {
        List<OrderAndOrderItem> orderAndOrderItemList = new ArrayList<>();
        try {
            List<Order> orderList = OrderRepository.getAllOrder();
            for (int i = 0; i < orderList.size(); i++) {
                //get order information
                Order order = orderList.get(i);
                int orderId = order.getOrderId(); //get order id
                int userId = order.getUserId(); //get userId
                Payment payment = PaymentRepository.getPaymentById(order.getPaymentId()); //get payment
                Date orderDate = order.getOrderDate(); //get order date
                Delivery delivery = DeliveryRepository.getDeliveryById(order.getDeliveryId());
                OrderStatus orderStatus = OrderStatusRepository.getOrderStatusById(order.getStatusId());
                String note = order.getNote(); //get note
                int totalPayment = order.getTotalPayment(); //get total payment
                Date paymentDate = order.getPaymentDate(); //get payment date

                //get order Items List
                List<ProductAndOrderItem> productAndOrderItemList = OrderRepository.getProductAndOrderItem(orderId);

                //set order information
                OrderAndOrderItem orderAndOrderItem = new OrderAndOrderItem(orderId, userId, payment, orderDate, delivery, orderStatus, note, totalPayment, paymentDate, productAndOrderItemList); // create new orderDetails
                orderAndOrderItemList.add(orderAndOrderItem);
            }
            return orderAndOrderItemList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/
    public static List<OrderAndOrderItem> getAllOrderDetails() throws Exception {
        String sql = "Select o.orderId, o.userId, o.paymentId, o.orderDate, o.deliveryId, o.statusId, o.note, o.totalPayment,o.paymentDate, " +
                "u.userRole, u.userName, u.userUid, u.email, u.phoneNumber, u.note, " +
                "p.paymentType, p.paymentCost, d.address, os.status " +
                "from Orders o " +
                "left join Users u on u.userId = o.userId " +
                "left join Payment p on p.paymentId = o.paymentId " +
                "left join Delivery d on d.deliveryId = o.deliveryId " +
                "left join OrderStatus os on os.statusId = o.statusId ";
        List<OrderAndOrderItem> orderAndOrderItemList = getOrderDetails(sql);
        return orderAndOrderItemList;
    }

    /*public static List<OrderAndOrderItem> getOrderDetailsByUserId(int userId) {
        List<OrderAndOrderItem> orderAndOrderItemList = new ArrayList<>();
        try {
            List<Order> orderList = OrderRepository.getOrderByUserId(userId);
            for (int i = 0; i < orderList.size(); i++) {
                //get order information
                Order order = orderList.get(i);
                int orderId = order.getOrderId(); //get order id
                Payment payment = PaymentRepository.getPaymentById(order.getPaymentId()); //get payment
                Date orderDate = order.getOrderDate(); //get order date
                Delivery delivery = DeliveryRepository.getDeliveryById(order.getDeliveryId());
                OrderStatus orderStatus = OrderStatusRepository.getOrderStatusById(order.getStatusId());
                String note = order.getNote(); //get note
                int totalPayment = order.getTotalPayment(); //get total payment
                Date paymentDate = order.getPaymentDate(); //get payment date

                //get order Items List
                List<ProductAndOrderItem> productAndOrderItemList = OrderRepository.getProductAndOrderItem(orderId);

                //set order information
                OrderAndOrderItem orderAndOrderItem = new OrderAndOrderItem(orderId, userId, payment, orderDate, delivery, orderStatus, note, totalPayment, paymentDate, productAndOrderItemList); // create new orderDetails
                orderAndOrderItemList.add(orderAndOrderItem);
            }
            return orderAndOrderItemList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    public static List<OrderAndOrderItem> getOrderDetailsByUserId(int userId) throws Exception {
        String sql = "Select o.orderId, o.userId, o.paymentId, o.orderDate, o.deliveryId, o.statusId, o.note, o.totalPayment,o.paymentDate, " +
                "u.userRole, u.userName, u.userUid, u.email, u.phoneNumber, u.note, " +
                "p.paymentType, p.paymentCost, d.address, os.status " +
                "from Orders o " +
                "left join Users u on u.userId = o.userId " +
                "left join Payment p on p.paymentId = o.paymentId " +
                "left join Delivery d on d.deliveryId = o.deliveryId " +
                "left join OrderStatus os on os.statusId = o.statusId " +
                "where u.userId = " + userId;
        List<OrderAndOrderItem> orderAndOrderItemList = getOrderDetails(sql);
        return orderAndOrderItemList;
    }

    /*public static OrderAndOrderItem getOrderDetailsByOrderId(int orderId) {
        OrderAndOrderItem orderAndOrderItem = new OrderAndOrderItem();
        try {
            Order order = OrderRepository.getOrderById(orderId);
            if(order.getOrderId() != 0) {
                //get order information
                int userId = order.getUserId(); //get userId
                Payment payment = PaymentRepository.getPaymentById(order.getPaymentId()); //get payment
                Date orderDate = order.getOrderDate(); //get order date
                Delivery delivery = DeliveryRepository.getDeliveryById(order.getDeliveryId());
                OrderStatus orderStatus = OrderStatusRepository.getOrderStatusById(order.getStatusId());
                String note = order.getNote(); //get note
                int totalPayment = order.getTotalPayment(); //get total payment
                Date paymentDate = order.getPaymentDate(); //get payment date

                //get order Items List
                List<ProductAndOrderItem> productAndOrderItemList = OrderRepository.getProductAndOrderItem(orderId);

                //set order information
                orderAndOrderItem = new OrderAndOrderItem(orderId, userId, payment, orderDate, delivery, orderStatus, note, totalPayment, paymentDate, productAndOrderItemList); // create new orderDetails
            }
            return orderAndOrderItem;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    public static OrderAndOrderItem getOrderDetailsByOrderId(int orderId) throws Exception {
        String sql = "Select o.orderId, o.userId, o.paymentId, o.orderDate, o.deliveryId, o.statusId, o.note, o.totalPayment,o.paymentDate, " +
                "u.userRole, u.userName, u.userUid, u.email, u.phoneNumber, u.note, " +
                "p.paymentType, p.paymentCost, d.address, os.status " +
                "from Orders o " +
                "left join Users u on u.userId = o.userId " +
                "left join Payment p on p.paymentId = o.paymentId " +
                "left join Delivery d on d.deliveryId = o.deliveryId " +
                "left join OrderStatus os on os.statusId = o.statusId " +
                "where orderId = " + orderId;
        List<OrderAndOrderItem> orderAndOrderItemList = getOrderDetails(sql);
        OrderAndOrderItem orderAndOrderItem = new OrderAndOrderItem();
        if(orderAndOrderItemList.size() > 0) orderAndOrderItem = orderAndOrderItemList.get(0);
        return orderAndOrderItem;
    }
}
