package Model;

import Model.XMLParser.TestParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by tutyb_000 on 10/11/2016.
 */
public class TestData {

    public static ArrayList<Test> tests = null;

    public static ArrayList<Test> getTests(String filePath) {
        if (tests == null || tests.isEmpty()) {
            try {
                tests = new ArrayList<Test>();

                File[] files = new File(filePath).listFiles();
                for (File file : files) {
                    TestParser testParser = new TestParser(new FileInputStream(file.toString()));
                    ArrayList<Test> thisTest = testParser.getTests();
                    for (Test test : thisTest) {
                        tests.add(test);
//                        System.out.println(test.getQuestion());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return tests;
    }
}
