/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import DAO.GameDAO;
import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.util.*;
import model.*;

/**
 *
 * @author lenovo
 */
@WebServlet(name = "StoreCategoryServlet", urlPatterns = {"/StoreCategoryServlet"})
public class StoreCategoryServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // gan giong nhu home store
        List<Product> list = null;

        String cid = request.getParameter("cid");
        int page = Integer.parseInt(request.getParameter("page"));

        System.out.println("cid: " + cid);
        System.out.println("page current: " + page);

        GameDAO dao = new GameDAO();
        List<Product> allP = dao.getProductByCategory(cid);

        // de check set size page - neu nhu sp co 6 thi khi chia 5 thi se ra 1 thi se thieu 1 sp
        // nen can check de set size dung
        request.setAttribute("page", page);
        if (allP.size() < 10 && allP.size() > 5) {
            request.setAttribute("sizeProduct", 2);
            System.out.println("size Product: " + 2);
        } else if (allP.size() < 5) {
            request.setAttribute("sizeProduct", 1);
            System.out.println("size Product: " + 1);
        } else {
            request.setAttribute("sizeProduct", (allP.size() / 5));
            System.out.println("size Product: " + (allP.size() / 5));
        }
        request.setAttribute("cid", cid);

        List<category> cl = dao.getAllCategory();
        request.setAttribute("categoryList", cl);

        List<Product> newP = dao.getNewProduct();

        request.setAttribute("newP", newP);

        for (int i = 0; i <= (allP.size() / 5); ++i) {
            if (((page - 1) == i)) {
                list = dao.PageProductByCategory(cid, i * 5);
            }
        }

        request.setAttribute("listP", list);
        request.getRequestDispatcher("store.jsp").forward(request, response);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
