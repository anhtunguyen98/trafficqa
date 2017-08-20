/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author tutyb
 */
@WebServlet(name = "Base", urlPatterns = {"/Base"})
public class Base extends HttpServlet {

    private JSONObject base = null;

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
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Base</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Base at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        getBase(request, response);
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

    private void getBase(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        String diem = null;
        try {
            diem = URLDecoder.decode(request.getParameter("diem"), "UTF-8");
        } catch (NullPointerException e) {
            diem = null;
        }
        String khoan = URLDecoder.decode(request.getParameter("khoan"), "UTF-8");
        String dieu = URLDecoder.decode(request.getParameter("dieu"), "UTF-8");
        String nd = URLDecoder.decode(request.getParameter("nd"), "UTF-8");

        if (base == null) {
            prepareBase(request.getServerName());
        }

        JSONObject res = new JSONObject();

        try {
            JSONObject jobj = base.getJSONObject(nd);
            jobj = jobj.getJSONObject(dieu);
            res.put("dieu", jobj.getString("tenDieu"));
            jobj = jobj.getJSONObject(khoan);
            res.put("khoan", jobj.getString("tenKhoan"));

            if (diem != null && jobj.has(diem)) {
                res.put("diem", jobj.getString(diem));
            }

            response.getWriter().write(res.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void prepareBase(String domain) {
        String path;

        if (domain.equals("localhost")) {
            path = getServletContext().getRealPath("/") + "Data/";
        } else {
            path = System.getenv("OPENSHIFT_DATA_DIR");
        }

        base = new JSONObject();

        base.put("12", fileToJSONObject(path + "base-json/12-2017-TT-BGTVT.json"));
        base.put("23", fileToJSONObject(path + "base-json/23-2008-QH12.json"));
        base.put("46", fileToJSONObject(path + "base-json/46_2016_ND-CP.json"));
        base.put("91", fileToJSONObject(path+"base-json/91-2015.json"));
    }

    private JSONObject fileToJSONObject(String filePath) {
        try {
            Scanner inp = new Scanner(new File(filePath), "UTF-8");
            String json = "";
            while (inp.hasNext()) {
                json += inp.nextLine();
            }

            return new JSONObject(json).getJSONObject("root");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Base.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
