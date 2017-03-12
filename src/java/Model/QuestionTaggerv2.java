package Model;

import Measure.LibConst;
import edu.stanford.nlp.ling.WordTag;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import vn.hus.nlp.tagger.VietnameseMaxentTagger;

/**
 *
 * @author tutyb
 */
public class QuestionTaggerv2 {

    private VietnameseMaxentTagger tagger;
    private File folder;

    public QuestionTaggerv2(String folderPath) {
        tagger = new VietnameseMaxentTagger(LibConst.VTB_TAGER);
        folder = new File(folderPath);
        run(folder);
    }

    private void run(File f) {
        File[] files = f.listFiles();

        for (File file : files) {
            if (file.getPath().contains("\\.")) {
                continue;
            }

            if (file.isFile()) {
                taggerRunning(file);
            } else {
                run(file);
            }
        }
    }

    private void taggerRunning(File file) {
        try {
            Scanner inp = new Scanner(file);
            String filePath = file.getPath();
            String newLine = "\r\n";
            PrintWriter print = new PrintWriter(
                    filePath.replace("Tagged Questions", "Split word tagged questions v2"), "UTF-8");

            print.write(inp.nextLine() + newLine);
//            System.out.println(file.getName());
            while (inp.hasNext()) {
                String line = inp.nextLine();
                if (line.contains("root") || line.contains("test")
                        || line.contains("answer") || line.contains("base")) {
                    print.write(line + newLine);
                    continue;
                }

                line = line.trim().replace("<question>", "").replace("</question>", "");

                ArrayList<String> trunks = splitIntoTrunks(line);

                String res = "";

                for (String trunk : trunks) {
                    res += trunkTagging(trunk) + " ";
                }

                print.write("\t\t<question>");

                print.write(res.trim());

                print.write("</question>" + newLine);
            }
            print.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(QuestionTaggerv2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(QuestionTaggerv2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ArrayList<String> splitIntoTrunks(String line) {
//        System.out.println(line);
        ArrayList<String> trunks = new ArrayList<String>();
        boolean notTagged = true;
        int index = 0;

        while (index < line.length()) {
            if (notTagged == true) {
                int pos = line.indexOf("<", index);
                if (pos == -1) {
                    String trunk = line.substring(index, line.length()).trim();
                    if (trunk.length() > 0) {
                        trunks.add(trunk);
                    }
                    break;
                }
                String trunk = line.substring(index, pos).trim();
                if (trunk.length() > 0) {
                    trunks.add(trunk);
                }
                index = pos;
            } else {
                int pos = line.indexOf(">", line.indexOf(">", index) + 1) + 1;
                String trunk = line.substring(index, pos).trim();
                if (trunk.length() > 0) {
                    trunks.add(trunk);
                }
                index = pos;
            }

            notTagged = !notTagged;
        }

        return trunks;
    }

    private String trunkTagging(String trunk) {
        String res = "";
        ArrayList<WordTag> words;
        String firstTag = "", lastTag = "";
        boolean notTagged = true;

        if (trunk.contains("<")) {
            notTagged = false;
        }

        if (!notTagged) {
            int index = trunk.indexOf(">");
            firstTag = trunk.substring(0, index + 1);
            lastTag = trunk.substring(trunk.indexOf("<", index), trunk.indexOf(">", index + 1) + 1);
            trunk = trunk.replaceAll("<.{1,4}>", "");
        }

        res += firstTag;

        words = (ArrayList<WordTag>) tagger.tagText2(trunk, LibConst.TOKENIZER_PROPERTIES);
        for (WordTag word : words) {
            res += "<" + word.tag() + ">" + word.word().replaceAll("\\s", "_") + "</" + word.tag() + "> ";
        }

        res = res.replaceAll(",>", "W>").replaceAll("\">", "W>")
                .replaceAll(";>", "W>").replaceAll("->", "W>").replaceAll(":>", "W>");

        return res.trim() + lastTag;
    }

    private boolean notWord(String word) {
        String regex = "\\<\\W>";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(word);

        return matcher.find();
    }

    public static void main(String[] args) {
        QuestionTaggerv2 questionTagger = new QuestionTaggerv2(
                "E:\\thien\\Learning\\NLP\\Project\\Data\\Tagged Questions");
//        VietnameseMaxentTagger tagger = new VietnameseMaxentTagger(LibConst.VTB_TAGER);
//        ArrayList<WordTag> words = (ArrayList<WordTag>) tagger.tagText2("vạch kẻ đường", LibConst.TOKENIZER_PROPERTIES);
//
//        for (WordTag word : words) {
//            System.out.println(word.word() + " " + word.tag());
//        }
    }
}
