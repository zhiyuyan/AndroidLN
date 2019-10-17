package com.example.yzy.androidln.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by yzy on 2019/10/17 0017.
 */
public class DomParseService implements IXmlParseService {
    @Override
    public List<Person> getPersonsByParseXml(InputStream is) throws Exception {
        List<Person> list = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(is);
        Element rootElement = document.getDocumentElement();
        // 得到person节点链表
        NodeList nodeList = rootElement.getElementsByTagName("person");
        if (nodeList == null || nodeList.getLength() == 0) {
            return null;
        }
        list = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Person person = new Person();
            Element element = (Element) nodeList.item(i);

            // 得到节点属性：id
            int id = Integer.parseInt(element.getAttribute("id"));
            person.setId(id);

            // 得到孩子节点：name
            NodeList nameList = element.getElementsByTagName("name");
            Element nameElement = (Element) nameList.item(0);
            String name = nameElement.getTextContent();
            person.setName(name);

            // 得到年龄：age
            NodeList ageList = element.getElementsByTagName("age");
            Element ageElement = (Element) ageList.item(0);
            String age = ageElement.getTextContent();
            person.setAge(Integer.parseInt(age));

            list.add(person);
        }

        return list;
    }
}
