/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Admin;

import Dal.CategoryProductDAO;
import Model.CategoryProduct;
import Util.UploadImg;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xuant
 */
@WebServlet(name = "CategoryProductServlet", urlPatterns = {"/categoryproduct"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)
public class CategoryProductServlet extends HttpServlet {

    private CategoryProductDAO cpd;

    @Override
    public void init() throws ServletException {
        super.init();
        cpd = new CategoryProductDAO(); // Khởi tạo DAO ở đây
    }

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
            out.println("<title>Servlet CategoryProductServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CategoryProductServlet at " + request.getContextPath() + "</h1>");
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
        List<CategoryProduct> cpList = cpd.getAllCategoryProduct();
        request.setAttribute("cpList", cpList);
        request.getRequestDispatcher("admin_category_product.jsp").forward(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        int cpId;
        if (action.equals("enable")) {
            enableCategoryProduct(request, response);
        } else if (action.equals("disable")) {
            disableCategoryProduct(request, response);
        } else if (action.equals("update")) {
            updateCategoryProduct(request, response);
        } else if (action.equals("add")) {
            addCategoryProduct(request, response);
        }
        doGet(request, response);

//        String CPName = request.getParameter("cpname");
//        Part part = request.getPart("file");//
//        UploadImg uim = new UploadImg();
//        String fileName = uim.extractFileName(part);//file name
//        String applicationPath = getServletContext().getRealPath("");
//        String UPLOAD_DIR = "img\\category_img";
//        String dbFileName = uim.upload(fileName, UPLOAD_DIR, applicationPath, part);
    }

    private void enableCategoryProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int cpId = Integer.parseInt(request.getParameter("cpid"));
        System.out.println("CATEGORY PRODUCT ID"+cpId);
        cpd.enableById(cpId);
    }

    private void disableCategoryProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int cpId = Integer.parseInt(request.getParameter("cpid"));
        System.out.println( "CATEGORY PRODUCT ID "+cpId);
        cpd.disableById(cpId);
    }

    private void updateCategoryProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int cpId = Integer.parseInt(request.getParameter("cpid"));
        String CPName = request.getParameter("cpname");
        Part part = request.getPart("file");//
        UploadImg uim = new UploadImg();
        String fileName = uim.extractFileName(part);//file name
        String applicationPath = getServletContext().getRealPath("");
        String UPLOAD_DIR = "img\\category_img";
        String dbFileName = uim.upload(fileName, UPLOAD_DIR, applicationPath, part);

        if (cpd.updateById(cpId, CPName, dbFileName) == 0) {
            request.setAttribute("errorupdate", "Can not update duplicated name");
        }
        request.getRequestDispatcher("categoryproduct").forward(request, response);
    }

    private void addCategoryProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String CPName = request.getParameter("cpname");
        Part part = request.getPart("file");//
        UploadImg uim = new UploadImg();
        String fileName = uim.extractFileName(part);//file name
        String applicationPath = getServletContext().getRealPath("");
        String UPLOAD_DIR = "img\\category_img";
        if (cpd.getCategoryProductByName(CPName) == null) {
            String dbFileName = uim.upload(fileName, UPLOAD_DIR, applicationPath, part);
            cpd.addCategoryProduct(CPName, dbFileName);
            request.setAttribute("successadd", "Thêm thành công!");
        } else {
            request.setAttribute("erroradd", "Mục này đã tồn tại!");
        }
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
