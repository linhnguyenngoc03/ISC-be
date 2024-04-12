package com.example.demo.repository;

import com.example.demo.dbConnection.DBUtils;
import com.example.demo.model.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressRepository {
    public static List<Address> getAllAddress() throws Exception {
        List<Address> addressList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from dbo.Address";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        Address address = new Address();
                        address.setAddressId(table.getInt("addressId"));
                        address.setUserId(table.getInt("userId"));
                        address.setAddress(table.getString("address"));
                        address.setDateCreate(table.getDate("dateCreate"));
                        address.setDateUpdate(table.getDate("dateUpdate"));
                        addressList.add(address);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return addressList;
    }

    public static Address getAddressById(int addressId) throws Exception {
        Address address = new Address();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * from dbo.Address where addressId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, addressId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        address.setAddressId(table.getInt("addressId"));
                        address.setUserId(table.getInt("userId"));
                        address.setAddress(table.getString("address"));
                        address.setDateCreate(table.getDate("dateCreate"));
                        address.setDateUpdate(table.getDate("dateUpdate"));
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return address;
    }

    //Create new Category
    public static boolean createAddress(Address address) throws Exception {
        Address category = new Address();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "INSERT INTO dbo.Address (userId, address, dateCreate) VALUES (?,?,?)";
                PreparedStatement pst = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pst.setInt(1, address.getUserId());
                pst.setString(2, address.getAddress());
                pst.setString(3, DBUtils.getCurrentDate());
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

    //Update Category
    public static boolean updateAddress(Address address) throws Exception {
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Update dbo.Address Set address = ?, dateUpdate = ? WHERE addressId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, address.getAddress());
                pst.setString(2, DBUtils.getCurrentDate());
                pst.setInt(3, address.getAddressId());
                pst.executeUpdate();
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

    public static  List<Address> getAddressByUserId(int userId) throws Exception {
        List<Address> addressList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * From Address Where userId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, userId);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        Address address = new Address();
                        address.setUserId(table.getInt("userId"));
                        address.setAddressId(table.getInt("addressId"));
                        address.setAddress(table.getString("address"));
                        address.setDateCreate(table.getDate("dateCreate"));
                        address.setDateUpdate(table.getDate("dateUpdate"));
                        addressList.add(address);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return addressList;
    }

    //Delete Category
    public static boolean deleteAddress(int[] addressId) throws Exception {
        int userId = getAddressById(addressId[0]).getUserId();
        List<Address> addressList = getAddressByUserId(userId);
        try {
            if (addressList.size() > 1) {
                Connection cn = DBUtils.makeConnection();
                int count = 0;
                if (cn != null) {
                    for (int i = 0; i < addressId.length; i++) {
                        String sql = "Delete from dbo.Address where addressId = ?";
                        PreparedStatement pst = cn.prepareStatement(sql);
                        pst.setInt(1, addressId[i]);
                        int row = pst.executeUpdate();
                        if (row > 0) count++;
                    }
                    if (count > 0) return true;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static  List<Address> getAddressByUserUid(String userUid) throws Exception {
        List<Address> addressList = new ArrayList<>();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * From Address a Join Users u on a.userId = u.userId Where u.userUid = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1, userUid);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        Address address = new Address();
                        address.setUserId(table.getInt("userId"));
                        address.setAddressId(table.getInt("addressId"));
                        address.setAddress(table.getString("address"));
                        address.setDateCreate(table.getDate("dateCreate"));
                        address.setDateUpdate(table.getDate("dateUpdate"));
                        addressList.add(address);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return addressList;
    }

    public static  Address getAddressByUserIdAndAddress(int userId, String txrAddress) throws Exception {
        Address address = new Address();
        try {
            Connection cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "Select * From Address Where userId = ? and address = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, userId);
                pst.setString(2, txrAddress);
                ResultSet table = pst.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        address.setUserId(table.getInt("userId"));
                        address.setAddressId(table.getInt("addressId"));
                        address.setAddress(table.getString("address"));
                        address.setDateCreate(table.getDate("dateCreate"));
                        address.setDateUpdate(table.getDate("dateUpdate"));
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return address;
    }
}
