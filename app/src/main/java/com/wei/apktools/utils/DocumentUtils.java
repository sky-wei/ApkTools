package com.wei.apktools.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.File;

/**
 * Created by jingcai.wei on 3/19/14.
 */
public class DocumentUtils {

    public static Document getDocument(File source) throws DocumentException {

        return new SAXReader().read(source);
    }
}
