/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crfsuite;

import com.github.jcrfsuite.CrfTagger;
import com.github.jcrfsuite.CrfTrainer;
import com.github.jcrfsuite.util.Pair;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Ngọc Thiện
 */
public class TestCrfsuite {

    public static void main(String[] args) throws IOException {
        Scanner inp = new Scanner(System.in);
        String command = inp.next();

        if (command.equals("train")) {
            String trainPath = inp.next();
            String trainCRFPath = inp.next();
            String modelPath = inp.next();

            CreateCrfsuiteFormFile.create(trainPath, trainCRFPath);

            CrfTrainer.train(trainCRFPath, modelPath);
        } else if (command.equals("test")) {
            String modelFile = inp.next();
            String testFile = inp.next();

            CrfTagger crfTagger = new CrfTagger(modelFile);
            List<List<Pair<String, Double>>> tagProbLists = crfTagger.tag(testFile);//list of tagged sentences

            // Compute accuracy
            int total = 0;
            int correct = 0;
            System.out.println("Gold\tPredict\tProbability");

            BufferedReader br = new BufferedReader(new FileReader(testFile));
            String line;
            for (List<Pair<String, Double>> tagProbs : tagProbLists) {//foreach tagged sentence
                for (Pair<String, Double> tagProb : tagProbs) {//foreach token
                    String prediction = tagProb.first;//predicted tag
                    Double prob = tagProb.second;//probability of that tag

                    line = br.readLine();
                    if (line.length() == 0) {
                        // End of the sentence, will get word from the next sentence
                        line = br.readLine();
                    }
                    String gold = line.split("\t")[0];//true tag

                    System.out.format("%s\t%s\t%.2f\n", gold, prediction, prob);
                    total++;
                    if (gold.equals(prediction)) {
                        correct++;
                    }
                }
                System.out.println();
            }
            br.close();

            System.out.format("Accuracy = %.2f%%\n", 100. * correct / total);
        }
    }
}
