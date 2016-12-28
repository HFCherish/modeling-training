package com.tw.orm;

import org.w3c.dom.*;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pzzheng on 12/28/16.
 */
public class XmlBasedObjectMapper extends AbstractObjectMapper {
    private XPath xPath = XPathFactory.newInstance().newXPath();

    void addXmlObjectMapper(File file) {

    }

    public List<ObjectDescriptor> parse(Document document) throws XPathExpressionException, ClassNotFoundException {
        NodeList objectNodes = (NodeList) xPath.evaluate("/mapper/object", document, XPathConstants.NODESET);

        List<ObjectDescriptor> descriptors = new ArrayList<>();
        for( int i=0; i<objectNodes.getLength(); i++ ) {
            descriptors.add(parseObjectNode((Element)objectNodes.item(i)));
        }
        return descriptors;
    }

    private ObjectDescriptor parseObjectNode(Element objectNode) throws ClassNotFoundException {
        Class<?> type = Class.forName(objectNode.getAttribute("type"));
        return new ObjectDescriptor(type);
    }
}
