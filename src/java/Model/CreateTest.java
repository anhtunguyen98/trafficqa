/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ngọc Thiện
 */
public class CreateTest {

    private static PrintWriter training, testing;
    private static final String newLine = "\r\n";

    public static void createTest(String version) {
        File file = new File("E:\\thien\\Learning\\NLP\\Project\\Data\\Folds" + version);
        for (int i = 1; i <= 5; i++) {
            try {
                training = new PrintWriter("E:\\thien\\Learning\\NLP\\Project\\Data\\Tests"
                        + version + "\\Test " + i + "\\Train.txt", "UTF-8");
                testing = new PrintWriter("E:\\thien\\Learning\\NLP\\Project\\Data\\Tests"
                        + version + "\\Test " + i + "\\Test.txt", "UTF-8");
                time(file, i);

                training.close();
                testing.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CreateTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(CreateTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void time(File file, int ignore) {
        File[] files = file.listFiles();

        for (int i = 1; i <= 5; i++) {
            if (i == ignore) {
                begin(files[i - 1], testing, 1);
            } else {
                begin(files[i - 1], training, 1);
            }
        }
    }

    private static void begin(File file, PrintWriter print, int action) {
        File files[] = file.listFiles();

        for (File f : files) {
            if (f.isFile()) {
                write(f, print, action);
            } else {
                begin(f, print, action);
            }
        }
    }

    private static void write(File f, PrintWriter print, int action) {
        try {
            Scanner inp = new Scanner(f);
            String sentence = "";

            while (inp.hasNext()) {
                String line = inp.nextLine();
                if (line.length() == 0) {
                    if (sentence.length() == 0) {
                        continue;
                    }
                    print.write(((action == 0) ? sentence.trim() : sentence) + newLine);
                    sentence = "";
                } else {
                    if (action == 0) {
                        sentence += line.split("\\s")[0] + " ";
                    } else {
                        sentence += line + newLine;
                    }
                }
            }

            inp.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreateTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
