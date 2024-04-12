package com.example.demo.repository;

import com.example.demo.dbConnection.DBUtils;
import com.example.demo.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static com.example.demo.repository.CartItemsRepository.*;

public class CartRepository {
    public static List<Cart> getAllCart() throws Exception {
        List<Cart> cartList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT * FROM dbo.Cart";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        Cart cart = new Cart();
                        cart.setCartId(table.getInt("cartId"));
                        cart.setUserId(table.getInt("userId"));
                        cartList.add(cart);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cartList;
    }

    public static Cart getCartByCartId(int cartId) throws Exception {
        Cart cart = new Cart();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT * FROM Cart WHERE cartId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, cartId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        cart.setCartId(table.getInt("cartId"));
                        cart.setUserId(table.getInt("userId"));
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cart;
    }

    public static Cart getCartByUserId(int userId) throws Exception {
        Cart cart = new Cart();
        try{
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT * FROM Cart WHERE userId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, userId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        cart.setCartId(table.getInt("cartId"));
                        cart.setUserId(table.getInt("userId"));
                    }
                    return cart;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Cart getCartByUserUid(String userUid) throws Exception {
        Cart cart = null;
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from Cart c Join Users u on c.userId = u.userId where u.userUid = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, userUid);
                ResultSet table = pst.executeQuery();
                if (table.next()) {
                    cart = new Cart();
                    cart.setCartId(table.getInt("cartId"));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cart;
    }

    //Create a new cart
    public static boolean createCart(Cart cart) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "INSERT INTO Cart (userId) VALUES (?)";

                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, cart.getUserId());

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

    //Delete Cart
    public static boolean deleteCart(int[] cartId) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            int count = 0;
            if (cn != null) {
                for (int i = 0; i < cartId.length; i++) {
                    String sql = "DELETE FROM Cart WHERE cartId = ?";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    pst.setInt(1, cartId[i]);
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

    public static List<CartAndCartItem> getCartAndCartItem() throws Exception {
        List<Cart> cartList = getAllCart();
        List<CartAndCartItem> cartAndCartItemList = new ArrayList<>();
        for(int i = 0; i < cartList.size(); i++){
            List<CartItems> cartItemsList = getCartItemsByCartId(cartList.get(i).getCartId());
            CartAndCartItem cartAndCartItem = new CartAndCartItem();
            cartAndCartItem.setCart(cartList.get(i));
            cartAndCartItem.setCartItems(cartItemsList);
            cartAndCartItemList.add(cartAndCartItem);
        }
        return cartAndCartItemList;
    }

    public static List<ProductAndCartItem> getProductAndCartItem(int cartId) throws Exception {
        List<ProductAndCartItem> productAndCartItemList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "select c.cartItemId, c.quantity, p.productId, p.productName, p.price, p.quantity as productQuantity, p.categoryId, p.description, p.status, p.image, p.dateCreate, p.dateUpdate " +
                        "from CartItems c " +
                        "left join Product p on p.productId = c.productId " +
                        "where c.cartId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, cartId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        ProductAndCartItem productAndCartItem = new ProductAndCartItem();
                        productAndCartItem.setCartItemId(table.getInt("cartItemId"));
                        productAndCartItem.setQuantity(table.getInt("quantity"));

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

                        productAndCartItem.setProduct(product);
                        productAndCartItemList.add(productAndCartItem);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return productAndCartItemList;
    }

    public static CartAndCartItemAndProduct getCartProductByUserUid(String userUid) throws Exception {
        Cart cart = getCartByUserUid(userUid);
        CartAndCartItemAndProduct cartAndCartItemAndProduct = new CartAndCartItemAndProduct();
        List<ProductAndCartItem> productAndCartItemList= getProductAndCartItem(cart.getCartId());
        cartAndCartItemAndProduct.setCart(cart);
        cartAndCartItemAndProduct.setProductAndCartItemList(productAndCartItemList);
        return cartAndCartItemAndProduct;
    }

}
