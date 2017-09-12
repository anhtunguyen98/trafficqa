/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import core.FindingAnswer;
import core.SimilarityComparing;
import static core.SimilarityComparing.tags;
import static core.SimilarityComparing.tagsMap;
import core.dao.AnswerDAO;
import core.dao.SaveTestDAO;
import core.model.TagContent;
import core.model.Test;
import crfsuite.TrafficCrfTagger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
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
    FindingAnswer findingAnswer;
    SaveTestDAO saveTestDAO = null;
    HashMap<String, String> replacer = null;
    Logger logger = null;

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

    //<editor-fold defaultstate="collapsed" desc="getAnswers">
    private void getAnswers(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException, IOException {
        autoInit(request.getServerName(), false);

        try {
            HashMap<String, String> hash = null;
            JSONObject jtags = null;
            JSONObject json = new JSONObject();
            String question;
            try {
                question = prepareQuestion(request.getParameter("question"));
            } catch (Exception e) {
                writeError(request, response, json, "Không có câu hỏi");
                return;
            }
            String tags = request.getParameter("tags");

            if (tags != null) {
                jtags = new JSONObject(tags);
                hash = parseToHash(jtags, question);
            } else {
                hash = findingAnswer.CRFToHash(question);
            }

            //<editor-fold defaultstate="collapsed" desc="handle for each case">
            if (!hash.containsKey("ano")) {
                boolean ok = true;
                String message = "";

                if (!hash.containsKey("qt")) {
                    if (hash.size() == 1) {
                        String tag = hash.keySet().iterator().next();
                        ArrayList<TagContent> tagContents = SimilarityComparing.getSimiContent(hash.get(tag), tag);

                        if (tagContents == null || tagContents.isEmpty()) {
                            message = "Câu hỏi không liên quan tới giao thông đường bộ!";
                            json.put("error", 1);
                        } else {
                            message = "Bạn muốn hỏi như thế nào!";
                        }
                    } else {
                        message = "Bạn muốn hỏi như thế nào!";
                    }
                    ok = false;
                } else if (hash.size() == 1) {
                    ArrayList<TagContent> tagContents = SimilarityComparing.getSimiContent(hash.get("qt"), "qt");

                    if (tagContents == null || tagContents.isEmpty()) {
                        message = "Câu hỏi không liên quan tới giao thông đường bộ!";
                        json.put("error", 1);
                    } else {
                        message = "Câu hỏi của bạn thiếu thông tin như là phương tiện hoặc hành động, v.v.";
                    }
                    ok = false;
                } else if (hash.containsKey("tv") && !(hash.containsKey("a")
                        || hash.containsKey("ac") || hash.containsKey("sp")
                        || hash.containsKey("if1") || hash.containsKey("if2")
                        || hash.containsKey("if3") || hash.containsKey("if4")
                        || hash.containsKey("dl"))) {
                    message = "Hãy nhập thêm hành động cho câu hỏi!";
                    ok = false;
                }

                if (!ok) {
                    json.put("hash_answer", false);
                    json.put("message", message);
                    json.put("tags", hash);
                    response.getWriter().write(json.toString());
                    return;
                }
            }//</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="find answers and return them">
            JSONObject jobj = findingAnswer.getAnswerWithHash(hash);
            ArrayList<core.model.Answer> answers = null;
            boolean success = jobj.getBoolean("success");

            if (success) {
                answers = new ArrayList<core.model.Answer>();
                JSONArray arr = (JSONArray) jobj.get("answers");
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    answers.add(new core.model.Answer(obj.getString("answer"), obj.getString("base"), obj.getJSONObject("tags")));
                }
            } else {
                if (jobj.has("error")) {
                    json.put("error", jobj.getInt("error"));
                }
            }

            if (answers == null || answers.isEmpty()) {
                json.put("has_answer", false);
            } else if (answers.size() > 1) {
                boolean has_answer = true;
                if (hash.containsKey("ano")) {
                    has_answer = true;
                } else if (!hash.containsKey("qt")) {
                    has_answer = false;
                } else if (containsSomeTv(answers)) {
                    json.put("error", 2);
                    json.put("message", "Bạn muốn hỏi về phương tiện gì?");
                    has_answer = false;
                }

                json.put("has_answer", has_answer);
            } else {
                json.put("has_answer", true);
            }

            writeResponse(request, response, answers, json);
            //</editor-fold>
        } catch (IOException e) {
            autoInit(request.getServerName(), true);
        } catch (JSONException e) {
            autoInit(request.getServerName(), true);
        }
    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="saveTest">
    private void saveTest(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException, IOException {
        try {
            autoInit(request.getServerName(), false);
            long time = new java.util.Date().getTime();
            if (!isLocal) {
                time -= (60 * 60 * 1000);
            }
            Timestamp createdDate = new Timestamp(time);
            String question = URLDecoder.decode(request.getParameter("question"), "UTF-8")
                    .trim().replaceAll("\\s+", " ");
            String answer = URLDecoder.decode(request.getParameter("answer"), "UTF-8");
            String query = URLDecoder.decode(request.getParameter("query"), "UTF-8");
            String tags = URLDecoder.decode(request.getParameter("tags"), "UTF-8");
            String satisfied = URLDecoder.decode(request.getParameter("satisfied"), "UTF-8");

            Test test = new Test(createdDate, question, answer, query, tags, satisfied);

            saveTestDAO.saveTest(test);
            JSONObject jobj = new JSONObject();
            jobj.put("success", true);
            response.getWriter().write(jobj.toString());
        } catch (IOException e) {
            logger.error(e);
        } catch (SQLException e) {
            logger.error(e);
        } catch (JSONException e) {
            logger.error(e);
        }
    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="prepare">
    private HashMap<String, String> parseToHash(JSONObject jtags, String addedInfo) {
        HashMap<String, String> hash = new HashMap<String, String>();

        for (String key : jtags.keySet()) {
            hash.put(key, jtags.getString(key));
        }

        HashMap<String, String> addedInfoHash = findingAnswer.CRFToHash(addedInfo);

        for (String key : addedInfoHash.keySet()) {
            hash.put(key, addedInfoHash.get(key));
        }

        return hash;
    }

    private void prepareTagsMap() {
        tagsMap = new HashMap<String, ArrayList<String>>();
        for (String tag : tags) {
            tagsMap.put(tag, new ArrayList<String>());

            try {
                Scanner inp = new Scanner(new File(DATA_PATH + "taggroups/" + tag + ".txt"), "UTF-8");
                String line;

                while (inp.hasNext()) {
                    line = inp.nextLine();
                    if (line.length() == 0) {
                        continue;
                    }

                    tagsMap.get(tag).add(line);
                }

            } catch (FileNotFoundException ex) {
                logger.info(ex);
            } catch (IOException ex) {
                logger.info(ex);
            }
        }
    }

    private void autoInit(String domain, boolean force) throws IOException {
        if (force || findingAnswer == null || findingAnswer.dao == null) {//create DAO
            createAllThings(domain);
        }
    }

    private void createAllThings(String domain) throws IOException {
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

        findingAnswer = new FindingAnswer();
        findingAnswer.dao = new AnswerDAO(domain, username, password, dbName);
        saveTestDAO = new SaveTestDAO(domain, username, password, dbName);

        if (DATA_PATH.length() == 0) {
            if (isLocal) {
                DATA_PATH = getServletContext().getRealPath("/") + "Data/";
            } else {
                DATA_PATH = System.getenv("OPENSHIFT_DATA_DIR");
            }

            prepareTagsMap();
            prepareReplacer();

            logger = Logger.getLogger(Answer.class);

            Const.Path.DATA_PATH = DATA_PATH;
        }
    }

    public void prepareReplacer() {
        if (replacer == null) {
            replacer = new HashMap<String, String>();

            try {
                Scanner inp = new Scanner(new File(DATA_PATH + "replacer.txt"), "UTF-8");

                while (inp.hasNext()) {
                    String line = inp.nextLine();
                    if (line.isEmpty()) {
                        continue;
                    }

                    String[] tokens = line.split("_");
                    String value = tokens[1];
                    tokens = tokens[0].split(",");
                    for (String token : tokens) {
                        replacer.put(token, value);
                    }
                }
            } catch (FileNotFoundException ex) {
                logger.info(ex);
            }
        }
    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Response">
    private void writeResponse(HttpServletRequest request, HttpServletResponse response,
            ArrayList<core.model.Answer> answers, JSONObject json)
            throws IOException {
        core.model.Answer ans;
        if (answers == null || answers.isEmpty()) {
            ans = null;
        } else {
            ans = answers.get(0);
        }

        String answer = ((ans == null || ans.getAnswer().length() == 0 || !json.getBoolean("has_answer"))
                ? "No Answers!" : ans.getAnswer());
        String base = ((ans == null || ans.getBase().length() == 0) ? "" : ans.getBase());

        json.put("answer", answer);
        json.put("base", base);
        json.put("tags", findingAnswer.jtags);
        json.put("query", AnswerDAO.foundedSql);

        response.getWriter().write(json.toString());
    }

    private void writeError(HttpServletRequest request,
            HttpServletResponse response, JSONObject json, String... messages)
            throws IOException {
        json.put("messages", messages);
        response.getWriter().write(json.toString());
    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="containsSomeTv">
    private boolean containsSomeTv(ArrayList<core.model.Answer> answers) {
        boolean isContain = false;
        String tv = "";
        int count = 0;

        for (core.model.Answer answer : answers) {
            JSONObject tags = answer.getTags();
            if (tags != null) {
                isContain = isContain || tags.has("tv");
                if (tags.has("tv")) {
                    String t = tags.getString("tv");
                    if (!tv.equals(t)) {
                        count++;
                        tv = t;
                    }
                }
            }
        }

        if (count < 2) {
            isContain = false;
        }

        return isContain;
    }//</editor-fold>

    private String prepareQuestion(String question) {
        for (String key : replacer.keySet()) {
            question = question.replaceAll(key.toLowerCase(), replacer.get(key));
        }

        return question;
    }
}
