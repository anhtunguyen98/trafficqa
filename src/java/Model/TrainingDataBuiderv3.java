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
 * @author tutyb
 */
public class TrainingDataBuiderv3 {

    private File folder;
    private final String newLine = "\r\n";

    public TrainingDataBuiderv3(String folderPath) {
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
            PrintWriter print = new PrintWriter(
                    filePath.replace("Training Data v2", "Training Data v3"), "UTF-8");

            inp.nextLine();
            while (inp.hasNext()) {
                String line = inp.nextLine();

                if (!line.contains("_")) {
                    print.write(line + newLine);
                } else {
                    String[] splits = line.split("\\s");
                    String[] words = splits[0].split("_");
                    int index = -1;
                    if (line.contains("B-")) {
                        index = 0;
                        splits[2] = splits[2].replace("B-", "");
                    }

                    for (String word : words) {
                        print.write(word + "\t" + splits[1] + "\t"
                                + ((index == -1) ? "" : ((index++ == 0) ? "B-" : "I-")) + splits[2] + newLine);
                    }
                }
            }
            print.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TrainingDataBuiderv3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TrainingDataBuiderv3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        TrainingDataBuiderv3 questionTagger = new TrainingDataBuiderv3(
                "E:\\thien\\Learning\\NLP\\Project\\Data\\Training Data v2");
//        VietnameseMaxentTagger tagger = new VietnameseMaxentTagger(LibConst.VTB_TAGER);
//        ArrayList<WordTag> words = (ArrayList<WordTag>) tagger.tagText2("vạch kẻ đường", LibConst.TOKENIZER_PROPERTIES);
//
//        for (WordTag word : words) {
//            System.out.println(word.word() + " " + word.tag());
//        }
    }
}
