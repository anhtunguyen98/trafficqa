/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crfsuite;

import com.github.jcrfsuite.util.Pair;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ngoc Thien
 */
public class TestTrafficCrfTagger {

    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);
        String modelFile = inp.next();
        String sentence = "ô tô chạy quá tốc độ quy định bị phạt bao nhiêu tiền";

        TrafficCrfTagger tagger = new TrafficCrfTagger(modelFile);
        try {
            List<Pair<String, String>> tags = tagger.tag(sentence);

            for (int i = 0; i < tags.size(); i++) {
                Pair<String, String> pair = tags.get(i);
                System.out.println(pair.getFirst() + " " + pair.getSecond());
            }
        } catch (IOException ex) {
            Logger.getLogger(TestTrafficCrfTagger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
