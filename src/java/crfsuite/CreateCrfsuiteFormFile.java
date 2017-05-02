/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crfsuite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Ngọc Thiện
 */
public class CreateCrfsuiteFormFile {

    public static int[] template = new int[]{-2, -1, 0, 1, 2};

    public static void create(String trainPath, String trainCRFPath) {

        try {
            Scanner tf = new Scanner(new File(trainPath));
            PrintWriter out = new PrintWriter(trainCRFPath, "UTF-8");
            ArrayList<String> words = new ArrayList<String>();
            ArrayList<String> tags = new ArrayList<String>();

            while (tf.hasNext()) {
                String line = tf.nextLine();
                if (line.length() == 0) {
                    convert(out, words, tags);
                    words.clear();
                    tags.clear();

                    continue;
                }

                String[] raw = line.split("\\s");
                words.add(raw[0]);
                tags.add(raw[2]);
            }

            if (words.size() > 0) {
                convert(out, words, tags);
            }

            out.close();
            tf.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    private static void convert(PrintWriter out, ArrayList<String> words, ArrayList<String> tags) {
        for (int i = 0; i < words.size(); i++) {
            out.print(tags.get(i));

            for (int j = 1; j <= 3; j++) {
                write(out, words, i, j);
            }
        }
    }

    private static void write(PrintWriter out, ArrayList<String> words, int index, int k) {
        if (k == 1) {
            for (int i : template) {
                int offset = index + i;
                if (isOK(offset, words.size())) {
                    out.print("\tw[" + i + "]=" + words.get(offset));
                }
            }
        } else if (k == 2) {
            for (int i = 0; i < template.length - 1; i++) {
                int offset1 = index + template[i];
                int offset2 = index + template[i + 1];

                if (isOK(offset1, words.size()) && isOK(offset2, words.size())) {
                    out.print("\tw[" + template[i] + "]|w[" + template[i + 1] + "]"
                            + "=" + words.get(offset1) + "|" + words.get(offset2));
                }
            }
        } else if (k == 3) {
            for (int i = 0; i < template.length - 2; i++) {
                int offset1 = index + template[i];
                int offset2 = index + template[i + 1];
                int offset3 = index + template[i + 2];

                if (isOK(offset1, words.size()) && isOK(offset2, words.size())
                        && isOK(offset3, words.size())) {
                    out.print("\tw[" + template[i] + "]|w[" + template[i + 1] + "]"
                            + "|w[" + template[i + 2] + "]="
                            + words.get(offset1) + "|" + words.get(offset2) + "|" + words.get(offset3));
                }
            }

            if (index == words.size() - 1) {
                out.print("\t__EOS__\r\n");
            }

            if (index == 0) {
                out.print("\t__BOS__");
            }

            out.print("\r\n");
        }
    }

    private static boolean isOK(int offset, int size) {
        return offset >= 0 && offset < size;
    }
}
