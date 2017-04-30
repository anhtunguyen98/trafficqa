/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Const.*;
import DAO.DAO;
import com.aliasi.crf.ChainCrf;
import com.aliasi.tag.Tagging;
import com.aliasi.util.AbstractExternalizable;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author tutyb
 */
@WebServlet(name = "Answer", urlPatterns = {"/Answer"})
public class Answer extends HttpServlet {

    String DATA_PATH = "";
    String modelPath = "";
    File modelFile = null;
    @SuppressWarnings("unchecked")
    ChainCrf<String> crf = null;
    DAO dao = null;

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
        response.setContentType("application/json;charset=UTF-8");
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
        doPost(request, response);
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

        String action = request.getParameter("action");

        if (action.equals("getAnswers")) {
            getAnswers(request, response);
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

    private void getAnswers(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException, IOException {
        if (dao == null) {
            dao = new DAO();
        }

        if (DATA_PATH.length() == 0) {
            DATA_PATH = getServletContext().getRealPath("/") + "Data/";
            Path.DATA_PATH = DATA_PATH;
            modelPath = DATA_PATH + "model";
            modelFile = new File(modelPath);
        }

        String question = URLDecoder.decode(request.getParameter("question"), "UTF-8");

        if (crf == null) {
            try {
                crf = (ChainCrf<String>) AbstractExternalizable.readObject(modelFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }

        List<String> tokens = Arrays.asList(question.split("\\s"));
        Tagging<String> tagging = crf.tag(tokens);

        HashMap<String, String> hash = new HashMap();
        String content = "";

        for (int i = 0; i < tagging.size(); i++) {
            String token = tagging.token(i);
            String tag = tagging.tag(i);

            if (!tag.equals("O")) {
                if (hash.containsKey(tag)) {
                    content = hash.get(tag) + " " + content;
                }
                hash.put(tag, content);
            }
        }

        String answer;

        try {
            answer = dao.getAnswers(hash);
        } catch (SQLException ex) {
            answer = "Something went wrong!";
        }

        answer = ((answer.length() == 0) ? "No Answers!" : answer);

        JSONObject json = new JSONObject();
        json.put("answer", answer);
        
        response.getWriter().write(json.toString());
    }

}
