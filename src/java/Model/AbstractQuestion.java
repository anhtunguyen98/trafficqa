/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Measure.LibConst;
import edu.stanford.nlp.ling.WordTag;
import java.util.ArrayList;
import vn.hus.nlp.tagger.VietnameseMaxentTagger;

/**
 *
 * @author tutyb_000
 */
public class AbstractQuestion {

    private String question;
    private ArrayList<ArrayList<String>> abs;

    public AbstractQuestion(String question) {
        this.question = question;
        abs = new ArrayList<ArrayList<String>>();
        createAbstract();
    }

    private void createAbstract() {
        createAbstract0();
        createAbstract1();
    }

    private void createAbstract0() {
        ArrayList<String> arr = new ArrayList<String>();
        for (String str : question.split("\\s+")) {
            arr.add(str);
        }
        abs.add(arr);
    }

    private void createAbstract1() {
        VietnameseMaxentTagger tagger = new VietnameseMaxentTagger(LibConst.VTB_TAGER);

        ArrayList<WordTag> wordTags1 = (ArrayList<WordTag>) tagger.tagText2(question, LibConst.TOKENIZER_PROPERTIES);

        ArrayList<String> s1, s2, s3, s4, s5, s6;
        s1 = new ArrayList<String>();
        s2 = new ArrayList<String>();
        s3 = new ArrayList<String>();
        s4 = new ArrayList<String>();
        s5 = new ArrayList<String>();
        s6 = new ArrayList<String>();

        for (WordTag wordTag : wordTags1) {
            String word = wordTag.word();
            String tag = wordTag.tag();
            s1.add(word);
            s2.add(tag);
            if (tag.equals("Np") || tag.equals("Nc") || tag.equals("Nu") || tag.equals("N")
                    || tag.equals("V") || tag.equals("A")) {
                s3.add(tag);
                if (!tag.equals("V") && !tag.equals("A")) {
                    s4.add(tag);
                }

                if (tag.equals("V")) {
                    s5.add(tag);
                }

                if (tag.equals("A")) {
                    s6.add(tag);
                }
            }
        }

        abs.add(s1);
        abs.add(s2);
        abs.add(s3);
        abs.add(s4);
        abs.add(s5);
        abs.add(s6);
    }

    public ArrayList<ArrayList<String>> getAbs() {
        return abs;
    }
}
