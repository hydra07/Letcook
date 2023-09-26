/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;
import model.User;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author xuant
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("repassword");
        String email = request.getParameter("email");

        //notyfi
        String mailError = "This email was registered!";
        String error = "Server error!";
        String success = "Register successfull!";
        String passwordError = "Password not the same!";

        //valid input
        String validName;
        String validpassword;
        String validPhone;

        String phoneRegex = "/^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$/";
        String passwordRegex = "^\\S{3,8}$";
        String nameRegex = "^\\S{2,50}(?:\\s\\S{2,50})*$";
        Pattern patternPW = Pattern.compile(passwordRegex);
        Pattern patternPhone = Pattern.compile(phoneRegex);
        Pattern patternName = Pattern.compile(nameRegex);
        boolean checkPassword = patternPW.matcher(password).matches();
        boolean checkPhone = patternPhone.matcher(phone).matches();
        boolean checkName = patternName.matcher(name).matches();

        UserDAO ud = new UserDAO();
        //Create account
        User user = ud.getUserByEmail(email);
        if (user != null) {
            request.setAttribute("mailError", mailError);
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
        //checking before create new account
        if (!checkPhone || !checkPassword || !checkName) {
            if (!checkPhone) {
                validPhone = "Invalid phone number!";
                request.setAttribute("validPhone", validPhone);
            }

            if (!checkName) {
                validName = "Name must be more than 2 digit";
                request.setAttribute("validName", validName);

            }

            if (!checkPassword) {
                validpassword = "Password length must be [3-8] and does not have space";
                request.setAttribute("validpassword", validpassword);
            }
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else if (!password.equals(confirmPassword)) {
            request.setAttribute("passwordError", passwordError);
            request.getRequestDispatcher("loadpage").forward(request, response);
        }

        ud.Register(name, password, phone, email);
        request.setAttribute("success", success);
        request.getRequestDispatcher("loadpage").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
