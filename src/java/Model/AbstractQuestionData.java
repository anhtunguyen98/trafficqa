/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author tutyb_000
 */
public class AbstractQuestionData {

    public static ArrayList<AbstractQuestion> absQuestions = null;

    public static ArrayList<AbstractQuestion> getAbsQuestions(String filePath) {
        if (absQuestions == null || absQuestions.isEmpty()) {
            ArrayList<Test> tests = TestData.getTests(filePath);
            absQuestions = new ArrayList<AbstractQuestion>();
            for (Test test : tests) {
                AbstractQuestion absQuestion = new AbstractQuestion(test.getQuestion());
                absQuestions.add(absQuestion);
            }
        }

        return absQuestions;
    }
}
