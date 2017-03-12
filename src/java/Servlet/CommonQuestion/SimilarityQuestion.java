/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet.CommonQuestion;

import Measure.LibConst;
import Measure.Measure;
import Model.AbstractQuestion;
import Model.AbstractQuestionData;
import Model.JSONPaser.Test2JSON;
import Model.Test;
import Model.TestData;
import Model.TestMeasure;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author tutyb_000
 */
@WebServlet(name = "SimilarityQuestion", urlPatterns = {"/SimilarityQuestion"})
public class SimilarityQuestion extends HttpServlet {

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
        String DATA_PATH = getServletContext().getRealPath("/") + "Data/";
        ArrayList<Test> tests = TestData.getTests(DATA_PATH + "CommonQuestion");
        Test2JSON t2j = new Test2JSON();
        JSONArray jarr = new JSONArray();

        for (Test test : tests) {
            jarr.put(t2j.convert2JSON(test));
        }

        JSONObject jobj = new JSONObject();
        jobj.put("tests", jarr);

        response.getWriter().write(jobj.toString());
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

        if (action.equals("getCommonQuestions")) {
            getCommonQuestions(request, response);
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

    private ArrayList<Test> getMostSimilarQuestions(String question, String DATA_PATH, int[] abs2Use, int mea) {
        ArrayList<Test> tests = TestData.getTests(DATA_PATH);
        ArrayList<Test> res = new ArrayList<Test>();
        ArrayList<TestMeasure> testMeasures = new ArrayList<TestMeasure>();
        ArrayList<AbstractQuestion> absqs = AbstractQuestionData.getAbsQuestions(DATA_PATH);
        AbstractQuestion userAbsq = new AbstractQuestion(question);

        for (int i = 0; i < tests.size(); i++) {
            Test test = tests.get(i);
            AbstractQuestion absq = absqs.get(i);
            TestMeasure testMeasure = new TestMeasure(test, abs2Use, mea);
            Measure measure = new Measure();
            measure.setAbs1(absq.getAbs());
            measure.setAbs2(userAbsq.getAbs());
            testMeasure.process(measure);
            testMeasures.add(testMeasure);
        }

        Collections.sort(testMeasures);

        for (int i = 0; i < 20; i++) {
            Test test = testMeasures.get(i).getTest();
            res.add(test);
        }

        return res;
    }

    private String test2JSON(ArrayList<Test> tests) {
        Test2JSON t2j = new Test2JSON();
        JSONArray jarr = new JSONArray();

        for (Test test : tests) {
            jarr.put(t2j.convert2JSON(test));
        }

        JSONObject jobj = new JSONObject();
        jobj.put("tests", jarr);

        return jobj.toString();
    }

    private void reconfigProperties() {
        try {
            PrintWriter print = new PrintWriter(LibConst.TOKENIZER_PROPERTIES);
            print.write("##\n"
                    + "## Properties for sentence detection \n"
                    + "##\n");
            print.write("sentDetectionModel=" + System.getenv("OPENSHIFT_DATA_DIR") + "models/sentDetection/VietnameseSD.bin.gz\n");
            print.write("##\n"
                    + "## Properties for tokenization\n"
                    + "##\n");
            print.write("sentDetectionModel=" + System.getenv("OPENSHIFT_DATA_DIR") + "models/sentDetection/VietnameseSD.bin.gz\n");
            print.write("lexiconDFA=" + System.getenv("OPENSHIFT_DATA_DIR") + "models/tokenization/automata/dfaLexicon.xml\n");
            print.write("externalLexicon=" + System.getenv("OPENSHIFT_DATA_DIR") + "models/tokenization/automata/externalLexicon.xml\n");
            print.write("normalizationRules=" + System.getenv("OPENSHIFT_DATA_DIR") + "models/tokenization/normalization/rules.txt\n");
            print.write("lexers=" + System.getenv("OPENSHIFT_DATA_DIR") + "models/tokenization/lexers/lexers.xml\n");
            print.write("unigramModel=" + System.getenv("OPENSHIFT_DATA_DIR") + "models/tokenization/bigram/unigram.xml\n");
            print.write("namedEntityPrefix=" + System.getenv("OPENSHIFT_DATA_DIR") + "models/tokenization/prefix/namedEntityPrefix.xml\n");

            print.flush();
            print.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SimilarityQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getDATA_PATH() {
        String DATA_PATH = getServletContext().getRealPath("/") + "Data/CommonQuestion";
//        String DATA_PATH = System.getenv("OPENSHIFT_DATA_DIR") + "CommonQuestion";
//        if (LibConst.TOKENIZER_PROPERTIES.equals("E:\\thien\\Learning\\NLP\\Project\\tokenizer.properties")) {
//            LibConst.TOKENIZER_PROPERTIES = System.getenv("OPENSHIFT_DATA_DIR") + "tokenizer.properties";
//            LibConst.VTB_TAGER = System.getenv("OPENSHIFT_DATA_DIR") + "resources/models/vtb.tagger";
//            reconfigProperties();
//        }

        return DATA_PATH;
    }

    private void getCommonQuestions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String DATA_PATH = getDATA_PATH();

        //get question
        String question = URLDecoder.decode(request.getParameter("question"), "UTF-8");
        //get abs2Use array
        String jabs2Use = URLDecoder.decode(request.getParameter("abs2Use"), "UTF-8");
        JSONArray jabsArr = new JSONArray(jabs2Use);
        int[] abs2Use = new int[jabsArr.length()];
        int mea = Integer.parseInt(URLDecoder.decode(request.getParameter("mea"), "UTF-8"));
        for (int i = 0; i < jabsArr.length(); i++) {
            abs2Use[i] = jabsArr.getJSONObject(i).getInt("abs");
        }

        //get most common tests
        ArrayList<Test> tests = getMostSimilarQuestions(question, DATA_PATH, abs2Use, mea);
//        ArrayList<Test> tests = TestData.getTests(DATA_PATH);
        //to json
        String resJson = test2JSON(tests);

        //write out
        response.getWriter().write(resJson);
    }
}
