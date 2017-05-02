/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crfsuite;

import com.github.jcrfsuite.util.CrfSuiteLoader;
import com.github.jcrfsuite.util.Pair;
import static crfsuite.CreateCrfsuiteFormFile.template;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import third_party.org.chokkan.crfsuite.Attribute;
import third_party.org.chokkan.crfsuite.Item;
import third_party.org.chokkan.crfsuite.ItemSequence;
import third_party.org.chokkan.crfsuite.StringList;
import third_party.org.chokkan.crfsuite.Tagger;

/**
 *
 * @author Ngoc Thien
 */
public class TrafficCrfTagger {

    static {
        try {
            CrfSuiteLoader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private final Tagger tagger = new Tagger();

    /**
     * Create a tagger using a model file.
     *
     * @param modelFile The file containing the model for this tagger.
     */
    public TrafficCrfTagger(String modelFile) {
        tagger.open(modelFile);
    }

    /**
     * Tag an item sequence. This method is synchronized so that you do not try
     * to label multiple sequences at the same time.
     *
     * @param xseq The input sequence.
     * @param sentence
     * @return For each item in the sequence, a {@link Pair} for each label with
     * the score for the label.
     */
    public synchronized List<Pair<String, String>> tag(ItemSequence xseq, String sentence) {
        String[] tokens = sentence.split("\\s");
        List<Pair<String, String>> predicted = new ArrayList<Pair<String, String>>();

        tagger.set(xseq);
        StringList labels = tagger.viterbi();
        for (int i = 0; i < labels.size(); i++) {
            String label = labels.get(i);
            predicted.add(new Pair<String, String>(tokens[i], label));
        }

        return predicted;
    }

    /**
     * Tag text in file. This calls a synchronized method so that you do not try
     * to label multiple sequences at the same time.
     *
     * @param sentence
     * @return For each sequence in the file, for each item in the sequence, a
     * {@link Pair} for each label with the score for the label
     * @throws IOException If there is a problem using the file.
     */
    public List<Pair<String, String>> tag(String sentence) throws IOException {
        return tag(toItemSequence(preparation(sentence)), sentence);
    }

    /**
     * @return The possible labels for this tagger.
     */
    public List<String> getlabels() {
        StringList labels = tagger.labels();
        int numLabels = (int) labels.size();
        List<String> result = new ArrayList<String>(numLabels);
        for (int labelIndex = 0; labelIndex < numLabels; ++labelIndex) {
            result.add(labels.get(labelIndex));
        }
        return result;
    }

    private String preparation(String sentence) {
        String[] words = sentence.split("\\s");
        String res = "";

        for (int i = 0; i < words.length; i++) {
            for (int j = 1; j <= 3; j++) {
                res += writePrepare(words, i, j);
            }
        }

        return res;
    }

    private ItemSequence toItemSequence(String preparation) {
        ItemSequence iseq = new ItemSequence();
        Item item = new Item();

        Scanner inp = new Scanner(preparation);

        while (inp.hasNext()) {
            String[] fields = inp.nextLine().split("\\s");
            for (String field : fields) {
                item.add(new Attribute(field));
            }

            iseq.add(item);
            item = new Item();
        }

        return iseq;
    }

    private String writePrepare(String[] words, int index, int k) {
        String res = "";

        if (k == 1) {
            for (int i : template) {
                int offset = index + i;
                if (isOK(offset, words.length)) {
                    res += "\tw[" + i + "]=" + words[offset];
                }
            }
        } else if (k == 2) {
            for (int i = 0; i < template.length - 1; i++) {
                int offset1 = index + template[i];
                int offset2 = index + template[i + 1];

                if (isOK(offset1, words.length) && isOK(offset2, words.length)) {
                    res += "\tw[" + template[i] + "]|w[" + template[i + 1] + "]"
                            + "=" + words[offset1] + "|" + words[offset2];
                }
            }
        } else if (k == 3) {
            for (int i = 0; i < template.length - 2; i++) {
                int offset1 = index + template[i];
                int offset2 = index + template[i + 1];
                int offset3 = index + template[i + 2];

                if (isOK(offset1, words.length) && isOK(offset2, words.length)
                        && isOK(offset3, words.length)) {
                    res += "\tw[" + template[i] + "]|w[" + template[i + 1] + "]"
                            + "|w[" + template[i + 2] + "]="
                            + words[offset1] + "|" + words[offset2] + "|" + words[offset3];
                }
            }

            if (index == words.length - 1) {
                res += "\t__EOS__\r\n";
            } else if (index == 0) {
                res += "\t__BOS__\r\n";
            } else {
                res += "\r\n";
            }
        }

        return res;
    }

    private boolean isOK(int offset, int size) {
        return offset >= 0 && offset < size;
    }
}
