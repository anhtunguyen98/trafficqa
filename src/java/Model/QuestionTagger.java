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
public class QuestionTagger {

    private VietnameseMaxentTagger tagger;
    private File folder;

    public QuestionTagger(String folderPath) {
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
                    filePath.replace("Tagged Questions", "Split word tagged questions"), "UTF-8");

            print.write(inp.nextLine() + newLine);
            System.out.println(file.getName());
            while (inp.hasNext()) {
                String line = inp.nextLine();
                if (line.contains("root") || line.contains("test")
                        || line.contains("answer") || line.contains("base")) {
                    print.write(line + newLine);
                    continue;
                }

                line = line.trim().replace("<question>", "").replace("</question>", "");

                ArrayList<Tag> tags = getTagFromSent(line);
                String res = "";

                line = line.replaceAll("<.{1,4}>", "").replaceAll("\\?", "");

                ArrayList<WordTag> wordTags = (ArrayList<WordTag>) tagger.tagText2(line, LibConst.TOKENIZER_PROPERTIES);
                print.write("\t\t<question>");

                for (WordTag wordTag : wordTags) {
                    res += "<" + wordTag.tag() + ">" + wordTag.word().replaceAll("\\s", "_") + "</" + wordTag.tag() + "> ";
                }

                res = merge(res.trim(), tags);

                print.write(res);

                print.write("</question>" + newLine);
            }

            print.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(QuestionTagger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(QuestionTagger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ArrayList<Tag> getTagFromSent(String line) {
        ArrayList<Tag> tags = new ArrayList<Tag>();
        String[] words = line.split("\\s");

        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            if (word.contains("<")) {
                Tag tag = new Tag();
                int index1 = word.indexOf("<");
                String tagName = word.substring(index1, word.indexOf(">") + 1);
                tag.setTag(tagName);
                tag.setNumOfSpace(i);

                tags.add(tag);

                int index2 = word.indexOf("<", index1 + 1);
                if (index2 != -1) {
                    tag = new Tag();
                    tagName = word.substring(index2, word.indexOf(">", index2 + 1) + 1);
                    tag.setTag(tagName);
                    tag.setNumOfSpace(i);

                    tags.add(tag);
                }
            }
        }

        return tags;
    }

    private String merge(String line, ArrayList<Tag> tags) {
        String res = "";
        String[] words = line.split("\\s");
        int i = 0;
        int lasti = 0;
        int numOfSpace = 0;

        for (Tag tag : tags) {
            int index = tag.getNumOfSpace();

            try {
                while (numOfSpace < index) {
                    if (notWord(words[i])) {
                        res = res.trim();
                        numOfSpace--;
                    }

                    res += words[i];
                    numOfSpace += countSpace(words[i++]);
                    if (numOfSpace < index) {
                        res += " ";
                        numOfSpace++;
                    }
                }

//                System.out.println(index + " " + tag.getTag() + " " + res);
                if (tag.getTag().contains("/")) {
                    if ((res.charAt(res.length() - 1) == ' ' || i == lasti) && numOfSpace == index) {
                        res += words[i] + tag.getTag() + " ";
                        numOfSpace += countSpace(words[i++]) + 1;
                    } else {
                        res += tag.getTag() + " ";
                        numOfSpace++;
                    }
                } else {
                    res += tag.getTag();
                    lasti = i;
                }
            } catch (Exception e) {
                System.out.println(res);
//                res = line;
            }
        }

        while (i <= words.length - 1) {
            res += words[i++] + " ";
        }

        return res;
    }

    private int countSpace(String line) {
        int index = line.indexOf("_");
        int count = 0;

        while (index != -1) {
            count++;
            index = line.indexOf("_", index + 1);
        }

        return count;
    }

    private boolean notWord(String word) {
        String regex = "\\<\\W>";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(word);

        return matcher.find();
    }

    public static void main(String[] args) {
        QuestionTagger questionTagger = new QuestionTagger(
                "E:\\thien\\Learning\\NLP\\Project\\Data\\Tagged Questions\\Law");
//        VietnameseMaxentTagger tagger = new VietnameseMaxentTagger(LibConst.VTB_TAGER);
//        ArrayList<WordTag> words = (ArrayList<WordTag>) tagger.tagText2("như thế nào?", LibConst.TOKENIZER_PROPERTIES);
//
//        for (WordTag word : words) {
//            System.out.println(word.word() + " " + word.tag());
//        }
    }
}
