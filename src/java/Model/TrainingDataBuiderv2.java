/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 *
 * @author Ngọc Thiện
 */
public class TrainingDataBuiderv2 {

    public static String[] trafficTags = {"tp", "tv", "if1", "a", "l", "tr", "ac",
        "sp", "v", "tl", "if2", "if3", "if4", "ti", "qt"};
    private final String newline = "\r\n";

    private XmlPullParser xpp;
    private XmlPullParserFactory factory;
    private InputStream inp;
    private PrintWriter print;

    public TrainingDataBuiderv2(String directory) {
        run(directory);
    }

    private void run(String directory) {
        File f = new File(directory);
        File[] files = f.listFiles();

        for (File file : files) {
            if (file.getPath().contains("\\.")) {
                continue;
            }

            if (file.isFile()) {
                buildData(file);
            } else {
                run(file.getPath());
            }
        }
    }

    private void buildData(File file) {
        try {
            inp = new FileInputStream(file);
            print = new PrintWriter(file.getPath().replace(
                    "Split word tagged questions v2", "Training Data v2 pre"), "UTF-8");

            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();
            xpp.setInput(inp, "UTF-8");
            String text = "";
            int event = xpp.getEventType();
            int count = 0;
            String trafficTag = "";
            int index = 0;
            String posTag = "";
            String thisTag = "";

            while (event != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();

                switch (event) {
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (isTaggetTag(tagName)) {
                            if (isTrafficTag(tagName)) {
                                trafficTag = tagName;
                                index = 0;
                            } else {
                                posTag = tagName;
                            }
                        }
                        thisTag = tagName;

                        break;
                    case XmlPullParser.TEXT:
                        if (thisTag.equals("root") || thisTag.equals("test")
                                || thisTag.equals("answer") || thisTag.equals("base") || thisTag.equals("question")) {
                            break;
                        }
                        text = xpp.getText();
                        if (text.equals(" ") || text.length() == 0) {
                            break;
                        }
                        print.write(text + "\t" + posTag.replaceAll("W", text) + "\t");
                        if (trafficTag.length() > 0) {
                            print.write(((index++ == 0) ? "B-" : "I-") + trafficTag + newline);
//                            print.write();
                        } else {
                            print.write("O" + newline);
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if (isTaggetTag(tagName)) {
                            if (isTrafficTag(tagName)) {
                                trafficTag = "";
                                index = 0;
                            } else {
                                posTag = "";
                            }
                        } else if (tagName.equals("test")) {
                            count += 5;
                        }
                        break;
                }

//                try {
                    event = xpp.next();
//                } catch (Exception ex) {
//                    print.write(",\t,\tO" + newline);
////                    Logger.getLogger(TrainingDataBuiderv2.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }

            print.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TrainingDataBuiderv2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TrainingDataBuiderv2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XmlPullParserException ex) {
//            print.write(",\t,\tO" + newline);
            System.out.println(file.getName());
//            Logger.getLogger(TrainingDataBuiderv2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TrainingDataBuiderv2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean isTrafficTag(String tag) {
        String[] tags = TrainingDataBuiderv2.trafficTags;

        for (String t : tags) {
            if (t.equals(tag)) {
                return true;
            }
        }

        return false;
    }

    private boolean isTaggetTag(String tagName) {
        String[] notTaggetTags = {"root", "test", "answer", "base", "question"};

        for (String tag : notTaggetTags) {
            if (tag.equals(tagName)) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        TrainingDataBuiderv2 buider = new TrainingDataBuiderv2(
                "E:\\thien\\Learning\\NLP\\Project\\Data\\Split word tagged questions v2");
    }
}
