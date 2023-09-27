package Dal;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author xuant
 */

import Model.User;
import Dal.DBcontext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

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
//        String phoneRegex = "^(0[1-9][0-9]{8}|84[1-9][0-9]{7})$";
//        String passwordRegex = "^(?!\\s)[\\S]{5,20}$";
//        String nameRegex = "^\\S{2,50}(?:\\s\\S{2,50})*$";
//        Pattern patternPW = Pattern.compile(passwordRegex);
//        Pattern patternPhone = Pattern.compile(phoneRegex);
//        Pattern patternName = Pattern.compile(nameRegex);
//        boolean checkPassword = patternPW.matcher("123555").matches();
//        boolean checkPhone = patternPhone.matcher("0372343515").matches();
//        boolean checkName = patternName.matcher("ciâ  ".trim()).matches();
//        System.out.println(checkName + " " + checkPhone + " " + checkPassword);
UserDAO ud = new UserDAO();
        System.out.println(ud.getUserByEmail("admintom@gmail.com").isIsAdmin());
    }
}
