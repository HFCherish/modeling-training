package com.tw.orm;

import com.tw.orm.testObjects.User;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by pzzheng on 12/28/16.
 */
public class XmlBasedObjectMapperTest {
    @Test
    public void should_parse_xml_document_to_object_descriptors() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, ClassNotFoundException, NoSuchFieldException {
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<mapper>" +
                    "<object type=\"com.tw.orm.testObjects.User\">" +
                    "</object>" +
                "</mapper>");
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(new ByteArrayInputStream(xmlBuilder.toString().getBytes("UTF-8")));

        XmlBasedObjectMapper xmlBasedObjectMapper = new XmlBasedObjectMapper();

        List<ObjectDescriptor> objectDescriptors = xmlBasedObjectMapper.parse(document);
        assertThat(objectDescriptors.size(), is(1));
        assertThat(objectDescriptors.get(0).getType().equals(User.class), is(true));
    }

    @Test
    public void should_parse_xml_document_with_property_descriptors() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, ClassNotFoundException, NoSuchFieldException {
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<mapper>" +
                    "<object type=\"com.tw.orm.testObjects.User\">" +
                        "<property name=\"username\" field=\"username\" />" +
                        "<property name=\"nickname\" field=\"nickname\" />" +
                    "</object>" +
                "</mapper>");
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(new ByteArrayInputStream(xmlBuilder.toString().getBytes("UTF-8")));

        XmlBasedObjectMapper xmlBasedObjectMapper = new XmlBasedObjectMapper();

        List<ObjectDescriptor> objectDescriptors = xmlBasedObjectMapper.parse(document);
        assertThat(objectDescriptors.size(), is(1));
        ObjectDescriptor objectDescriptor = objectDescriptors.get(0);
        PropertyDescriptor usernameProperty = objectDescriptor.getPropertyDescriptor("username");
        PropertyDescriptor nicknameProperty = objectDescriptor.getPropertyDescriptor("nickname");
        assertThat(usernameProperty.getPropertyType(), is(equalTo(String.class)));
        assertThat(usernameProperty.getFieldName(), is("username"));
        assertThat(usernameProperty.getPropertyName(), is("username"));
        assertThat(nicknameProperty.getPropertyType(), is(equalTo(String.class)));
    }
}