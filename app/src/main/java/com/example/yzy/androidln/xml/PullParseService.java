package com.example.yzy.androidln.xml;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzy on 2019/10/17 0017.
 */
public class PullParseService implements IXmlParseService {
    @Override
    public List<Person> getPersonsByParseXml(InputStream is) throws Exception {
        List<Person> list = null;
        Person person = null;
        String currentTag = null;

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(is, "utf-8");
        int type = parser.getEventType();
        while (type != XmlPullParser.END_DOCUMENT) {
            String typeName = parser.getName();
            if (type == XmlPullParser.START_TAG) {
                Log.v("yan", "typeName = " + typeName);
                if ("persons".equals(typeName)) {
                    list = new ArrayList<>();
                } else if ("person".equals(typeName)) {
                    person = new Person();
                    int id = Integer.parseInt(parser.getAttributeValue(0));
                    person.setId(id);
                } else if ("name".equals(typeName)) {
                    currentTag = "name";
                } else if ("age".equals(typeName)) {
                    currentTag = "age";
                }
            } else if (type == XmlPullParser.END_TAG) {
                if ("person".equals(typeName)) {
                    list.add(person);
                    person = null;
                }
            } else if (type == XmlPullParser.TEXT) {
                String s = parser.getText();
                if ("name".equals(currentTag)) {
                    person.setName(s);
                    currentTag = null;
                } else if ("age".equals(currentTag)) {
                    person.setAge(Integer.parseInt(s));
                    currentTag = null;
                }
            }
            type = parser.next();
        }
        Log.v("yan", "size = " + list.size());
        return list;
    }
}
