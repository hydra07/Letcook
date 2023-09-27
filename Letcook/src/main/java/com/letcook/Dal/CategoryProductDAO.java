/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.letcook.Dal;

import com.letcook.Model.CategoryProduct;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author xuant
 */
public class CategoryProductDAO extends DBcontext {
    //get category by id

    public CategoryProduct getCategoryProductById(int id) {
        List<CategoryProduct> list = new ArrayList<>();
        String sql = "SELECT * FROM CategoryProduct WHERE CategoryID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                CategoryProduct c = new CategoryProduct(rs.getInt("CategoryID"),
                        rs.getString("CategoryName"), rs.getString("img"),
                        rs.getBoolean("isEnabled"));
//                connection.close();
                return c;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public CategoryProduct getCategoryProductByName(String categoryProductName) {
        List<CategoryProduct> list = new ArrayList<>();
        String sql = "SELECT * FROM CategoryProduct WHERE CategoryName = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, categoryProductName);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                CategoryProduct c = new CategoryProduct(rs.getInt("CategoryID"),
                        rs.getString("CategoryName"), rs.getString("img"),
                        rs.getBoolean("isEnabled"));
//                connection.close();
                return c;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    //get all category in tabble
    public List<CategoryProduct> getAllCategoryProduct() {
        List<CategoryProduct> list = new ArrayList<>();
        String sql = "SELECT * FROM CategoryProduct";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                CategoryProduct c = new CategoryProduct(rs.getInt("CategoryID"),
                        rs.getString("CategoryName"), rs.getString("img"),
                        rs.getBoolean("isEnabled"));
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public void enableById(int id) {
        String sql = "UPDATE CategoryProduct SET isEnabled = 1 WHERE CategoryID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            int rowsUpdated = st.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Enable success.");
            } else {
                System.out.println("Not Found");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    public void disableById(int id) {
        String sql = "UPDATE CategoryProduct SET isEnabled = 0 WHERE CategoryID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            int rowsUpdated = st.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Disable success");
            } else {
                System.out.println("Not found");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    public int updateById(int id, String categoryProductName, String img) {
        int rowUpdate = 0;
        if (getCategoryProductByName(categoryProductName) != null) {
            System.out.println("Category Name existed");
            return rowUpdate;
        }
        String sql = "UPDATE Categories SET ProductName = ?, img = ? WHERE CategoryID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, categoryProductName);
            st.setString(2, img);
            st.setInt(3, id);
            rowUpdate = st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return rowUpdate;
    }

    public void addCategoryProduct(String categoryProductName, String img) {
        String sql = "INSERT INTO CategoryProduct (CategoryName, img) VALUES (?, ?)";
        if (getCategoryProductByName(categoryProductName) != null) {
            System.out.println("Category Name existed");
            return;
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, categoryProductName);
            st.setString(2, img);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding category product: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        CategoryProductDAO cpd = new CategoryProductDAO();
//        System.out.println(cpd.getCategoryProductByName("meat"));
        System.out.println(cpd.getAllCategoryProduct().get(0).getName());
        cpd.disableById(6);
    }

}
