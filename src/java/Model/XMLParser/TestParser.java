package Model.XMLParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import Const.XMLTag;
import Model.Test;

/**
 * Created by tutyb_000 on 10/11/2016.
 */

public class TestParser extends XMLParser implements Serializable {

    private ArrayList<Test> tests = null;

    public TestParser(InputStream inputStream) {
        super(inputStream);
    }

    public ArrayList<Test> getTests() {
        try {
            tests = new ArrayList<Test>();

            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();
            xpp.setInput(inputStream, "UTF-8");

            Test test = null;
            String text = null;

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();

                switch (eventType) {
                    case XmlPullParser.END_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        if (tagName.equals(XMLTag.TEST)) {
                            test = new Test();
                        }

                        break;

                    case XmlPullParser.TEXT:
                        text = xpp.getText();

                        break;

                    case XmlPullParser.END_TAG:
                        if (tagName.equals(XMLTag.QUESTION)) {
                            test.setQuestion(text);
                        } else if (tagName.equals(XMLTag.BASE)) {
                            test.setBase(text);
                        } else if (tagName.equals(XMLTag.ANSWER)) {
                            test.setAnswer(text);
                        } else if (tagName.equals(XMLTag.TEST)) {
                            tests.add(test);
                        }

                        break;
                }

                eventType = xpp.next();
            }

            return tests;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
