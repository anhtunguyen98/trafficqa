package Model.XMLParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by tutyb_000 on 10/11/2016.
 */

public class XMLParser implements Serializable {
    protected XmlPullParserFactory factory = null;
    protected XmlPullParser xpp = null;
    protected InputStream inputStream;

    public XMLParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
