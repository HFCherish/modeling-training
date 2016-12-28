package com.tw.orm;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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

    public List<ObjectDescriptor> parse(Document document) throws XPathExpressionException, ClassNotFoundException, NoSuchFieldException {
        NodeList objectNodes = (NodeList) xPath.evaluate("/mapper/object", document, XPathConstants.NODESET);

        List<ObjectDescriptor> descriptors = new ArrayList<>();
        for (int i = 0; i < objectNodes.getLength(); i++) {
            descriptors.add(parseObjectNode((Element) objectNodes.item(i)));
        }
        return descriptors;
    }

    private ObjectDescriptor parseObjectNode(Element objectNode) throws ClassNotFoundException, XPathExpressionException, NoSuchFieldException {
        Class<?> type = Class.forName(objectNode.getAttribute("type"));
        ObjectDescriptor objectDescriptor = new ObjectDescriptor(type);
        NodeList propertyNodes = (NodeList) xPath.evaluate("./property", objectNode, XPathConstants.NODESET);
        for (int i = 0; i < propertyNodes.getLength(); i++) {
            Element element = (Element) propertyNodes.item(i);
            String propertyName = element.getAttribute("name");
            String fieldName = element.getAttribute("field");
            Class<?> propertyType = type.getDeclaredField(propertyName).getType();
            objectDescriptor.addPropertyDescriptor(new PropertyDescriptor(fieldName, propertyName, propertyType));
        }
        return objectDescriptor;
    }
}
