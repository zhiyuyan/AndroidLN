package com.example.yzy.androidln.xml;

import java.io.InputStream;
import java.util.List;

/**
 * Created by yzy on 2019/10/16 0016.
 */
public interface IXmlParseService {

    List<Person> getPersonsByParseXml(InputStream is) throws Exception;

}
