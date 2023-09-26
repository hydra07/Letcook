/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.letcook.DAO;

import com.letcook.model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author xuant
 */
public class UserDAO extends DBcontext {

    public UserDAO() {
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE UserEmail = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getInt("UserID"), rs.getString("UserPassword"),
                        rs.getString("UserName"), rs.getString("phone"),
                        rs.getString("UserEmail"), rs.getBoolean("isAdmin"));

                return user;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }
    
     public void Register(String name, String password, String phone, String email) {
        String sql = "INSERT INTO [dbo].[Users]\n"
                + "           ([UserName]\n"
                + "           ,[UserPassword]\n"
                + "           ,[UserPhone]\n"
                + "           ,[UserEmail]\n"
                + "           ,[isAdmin])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,0)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, password);
            st.setString(3, phone);
            st.setString(4, email);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
     
     public User check(String Email, String password) {
        String sql = "SELECT *\n"
                + "  FROM [dbo].[Users]\n"
                + "  WHERE [UserEmail] =?\n"
                + "  AND [UserPassword] = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, Email);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getInt("UserID"), rs.getString("UserPassword"),
                        rs.getString("UserName"), rs.getString("UserPhone"),
                        rs.getString("UserEmail"), rs.getBoolean("isAdmin"));        
//                NotifyDAO nd = new NotifyDAO();
//                user.setNotifications(nd.getUserNotification(user.getUserID()));
                return user;

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main(String[] args) {
        UserDAO ud = new UserDAO();
        User user = ud.getUserByEmail("sss@gmail.com");
    }
}
