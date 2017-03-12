/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ngọc Thiện
 */
public class BuildingAll {

    public static void main(String[] args) {
        QuestionTaggerv2 questionTagger = new QuestionTaggerv2(
                "E:\\thien\\Learning\\NLP\\Project\\Data\\Tagged Questions");
        System.out.println("tagged");
        TrainingDataBuiderv2 buiderv2 = new TrainingDataBuiderv2(
                "E:\\thien\\Learning\\NLP\\Project\\Data\\Split word tagged questions v2");
        run("E:\\thien\\Learning\\NLP\\Project\\Data\\Training Data v2 pre");
        System.out.println("done v2");
        TrainingDataBuiderv3 builderv3 = new TrainingDataBuiderv3(
                "E:\\thien\\Learning\\NLP\\Project\\Data\\Training Data v2");
        System.out.println("done v3");
        SplitData.run("E:\\thien\\Learning\\NLP\\Project\\Data\\Training Data v2",
                "\\Folds\\v2");
        System.out.println("done split v2");
        SplitData.run("E:\\thien\\Learning\\NLP\\Project\\Data\\Training Data v3",
                "\\Folds\\v3");
        System.out.println("done split v3");
        CreateTest.createTest("\\v2");
        System.out.println("done create test v2");
        CreateTest.createTest("\\v3");
        System.out.println("done create test v3");
    }

    private static void run(String directory) {
        File f = new File(directory);
        File[] files = f.listFiles();

        for (File file : files) {
            if (file.getPath().contains("\\.")) {
                continue;
            }

            if (file.isFile()) {
                replace(file);
            } else {
                run(file.getPath());
            }
        }
    }

    private static void replace(File file) {
        try {
            Scanner inp = new Scanner(file);
            PrintWriter print = new PrintWriter(file.getPath()
                    .replace("Training Data v2 pre", "Training Data v2"));

            while (inp.hasNext()) {
                String line = inp.nextLine();
                if (line.contains("\t\t")) {
                    continue;
                }

                print.write(line + "\r\n");
            }

            print.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BuildingAll.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
