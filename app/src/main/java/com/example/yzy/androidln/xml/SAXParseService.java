package com.example.yzy.androidln.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by yzy on 2019/10/17 0017.
 */
public class SAXParseService implements IXmlParseService {
    @Override
    public List<Person> getPersonsByParseXml(InputStream is) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        MyHandler handler = new MyHandler();
        parser.parse(is, handler);
        return handler.getList();
    }

    private class MyHandler extends DefaultHandler {
        private List<Person> list;
        private Person person;
        private String currentTag;

        private List<Person> getList() {
            return list;
        }

        @Override
        public void startDocument() throws SAXException {
            list = new ArrayList<>();
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if ("person".equals(qName)) {
                person = new Person();
                person.setId(Integer.parseInt(attributes.getValue("id")));
            } else if ("name".equals(qName) || "age".equals(qName)) {
                currentTag = qName;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            String s = new String(ch, start, length);
            if ("name".equals(currentTag)) {
                person.setName(s);
            } else if ("age".equals(currentTag)) {
                person.setAge(Integer.parseInt(s));
            }
            currentTag = null;
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if ("person".equals(qName)) {
                list.add(person);
                person = null;
            }
        }
    }
}
