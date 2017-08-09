/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import core.FindingAnswer;
import static core.SimilarityComparing.tags;
import static core.SimilarityComparing.tagsMap;
import core.dao.AnswerDAO;
import core.dao.SaveTestDAO;
import core.model.Test;
import crfsuite.TrafficCrfTagger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
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
    AnswerDAO dao = null;
    boolean isLocal = true;
    TrafficCrfTagger tagger = null;
    FindingAnswer findingAnswer = new FindingAnswer();
    SaveTestDAO saveTestDAO = null;

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

//        System.out.println(action);
        if (action.equals("getAnswer")) {
            getAnswers(request, response);
        }

        if (action.equals("saveTest")) {
            saveTest(request, response);
        }

        if (action.equals("reGetAnswer")) {
            reGetAnswer(request, response);
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
        autoInit(request.getServerName());

        String question = URLDecoder.decode(request.getParameter("question"), "UTF-8").trim().replaceAll("\\s+", " ");

        ArrayList<core.model.Answer> answers = findingAnswer.getAnswer(question);
        JSONObject json = new JSONObject();

        if (answers == null || answers.size() > 1) {
            boolean tv = findingAnswer.jtags.has("tv");
            boolean qt = findingAnswer.jtags.has("qt");
            boolean a = findingAnswer.jtags.has("a");

            json.put("tv", tv);
            json.put("qt", qt);
            json.put("a", a);
            json.put("has_answer", false);
        } else {
            json.put("has_answer", true);
        }

        writeResponse(request, response, answers, json);
    }

    private void reGetAnswer(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        String tags = URLDecoder.decode(request.getParameter("tags"), "UTF-8");
        JSONObject jtags = new JSONObject(tags);
        String tv = URLDecoder.decode(request.getParameter("tv"), "UTF-8");
        String qt = URLDecoder.decode(request.getParameter("qt"), "UTF-8");
        String a = URLDecoder.decode(request.getParameter("a"), "UTF-8");

        HashMap<String, String> hash = parseToHash(jtags, tv, qt, a);
        ArrayList<core.model.Answer> answers = findingAnswer.getAnswerWithHash(hash);
        JSONObject json = new JSONObject();

        writeResponse(request, response, answers, json);
    }

    private void saveTest(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException, IOException {
        autoInit(request.getServerName());
        String question = URLDecoder.decode(request.getParameter("question"), "UTF-8").trim().replaceAll("\\s+", " ");
        String answer = URLDecoder.decode(request.getParameter("answer"), "UTF-8");
        String query = URLDecoder.decode(request.getParameter("query"), "UTF-8");
        String tags = URLDecoder.decode(request.getParameter("tags"), "UTF-8");
        String satisfied = URLDecoder.decode(request.getParameter("satisfied"), "UTF-8");

        Test test = new Test(question, answer, query, tags, satisfied);

        saveTestDAO.saveTest(test);
        JSONObject jobj = new JSONObject();
        jobj.put("success", true);
        response.getWriter().write(jobj.toString());
    }

    private HashMap<String, String> parseToHash(JSONObject jtags, String tv, String qt, String a) {
        HashMap<String, String> hash = new HashMap<String, String>();

        for (String key : jtags.keySet()) {
            hash.put(key, jtags.getString(key));
        }

        if (tv.length() > 0) {
            hash.put("tv", tv);
        }
        if (qt.length() > 0) {
            hash.put("qt", qt);
        }
        if (a.length() > 0) {
            hash.put("a", a);
        }

        return hash;
    }

    private void prepareTagsMap() {
        tagsMap = new HashMap<String, ArrayList<String>>();
        for (String tag : tags) {
            tagsMap.put(tag, new ArrayList<String>());

            try {
                Scanner inp = new Scanner(new File(DATA_PATH + "taggroups/" + tag + ".txt"));
                String line;

                while (inp.hasNext()) {
                    line = inp.nextLine();
                    if (line.length() == 0) {
                        continue;
                    }

                    tagsMap.get(tag).add(line);
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Answer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Answer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void autoInit(String domain) {
        if (findingAnswer.dao == null) {//create DAO
            String username, password, dbName;
            if (domain.equals("localhost")) {
                username = "root";
                password = "";
                dbName = "qaservice";
                isLocal = true;
            } else {
                domain = "127.12.52.2";
                username = "adminFf1Pbuj";
                password = "c5h8CW-Kb-HV";
                dbName = "QADatabase";
                isLocal = false;
            }

            findingAnswer.dao = new AnswerDAO(domain, username, password, dbName);
            saveTestDAO = new SaveTestDAO(domain, username, password, dbName);

            if (DATA_PATH.length() == 0) {
                if (isLocal) {
                    DATA_PATH = getServletContext().getRealPath("/") + "Data/";
                } else {
                    DATA_PATH = System.getenv("OPENSHIFT_DATA_DIR");

                    prepareTagsMap();
                }
                Const.Path.DATA_PATH = DATA_PATH;
            }
        }
    }

    private void writeResponse(HttpServletRequest request, HttpServletResponse response, ArrayList<core.model.Answer> answers, JSONObject json) throws IOException {
        core.model.Answer ans;
        if (answers == null || answers.isEmpty()) {
            ans = null;
        } else {
            ans = answers.get(0);
        }

        String answer = ((ans == null || ans.getAnswer().length() == 0) ? "No Answers!" : ans.getAnswer());
        String base = ((ans == null || ans.getBase().length() == 0) ? "" : ans.getBase());

        json.put("answer", answer);
        json.put("base", base);
        json.put("tags", findingAnswer.jtags);
        json.put("query", AnswerDAO.foundedSql);

        response.getWriter().write(json.toString());
    }
}
