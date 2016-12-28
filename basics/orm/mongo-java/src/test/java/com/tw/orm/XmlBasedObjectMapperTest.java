package com.tw.orm;

import com.tw.orm.testObjects.User;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by pzzheng on 12/28/16.
 */
public class XmlBasedObjectMapperTest {

    private XmlBasedObjectMapper xmlBasedObjectMapper;
    private DocumentBuilder documentBuilder;

    @Before
    public void setUp() throws ParserConfigurationException {
        xmlBasedObjectMapper = new XmlBasedObjectMapper();
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    @Test
    public void should_parse_xml_document_to_object_descriptors() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, ClassNotFoundException, NoSuchFieldException {
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<mapper>" +
                    "<object type=\"com.tw.orm.testObjects.User\">" +
                    "</object>" +
                "</mapper>");
        Document document = documentBuilder.parse(new ByteArrayInputStream(xmlBuilder.toString().getBytes("UTF-8")));

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
        Document document = documentBuilder.parse(new ByteArrayInputStream(xmlBuilder.toString().getBytes("UTF-8")));

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

    @Test
    public void should_parse_xml_document_with_property_descriptors_with_id() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, ClassNotFoundException, NoSuchFieldException {
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<mapper>" +
                    "<object type=\"com.tw.orm.testObjects.User\">" +
                        "<property name=\"id\" field=\"_id\" isId=\"true\"/>" +
                    "</object>" +
                "</mapper>");
        Document document = documentBuilder.parse(new ByteArrayInputStream(xmlBuilder.toString().getBytes("UTF-8")));

        List<ObjectDescriptor> objectDescriptors = xmlBasedObjectMapper.parse(document);
        assertThat(objectDescriptors.size(), is(1));
        ObjectDescriptor objectDescriptor = objectDescriptors.get(0);
        PropertyDescriptor idProperty = objectDescriptor.getPropertyDescriptor("id");
        assertThat(idProperty.getPropertyType(), is(equalTo(String.class)));
        assertThat(idProperty.getFieldName(), is("_id"));
        assertThat(idProperty.getPropertyName(), is("id"));
        assertThat(idProperty.isId(), is(true));
    }

    @Test
    public void should_able_to_add_xml_file() throws SAXException, IOException, XPathExpressionException, ClassNotFoundException, ParserConfigurationException, NoSuchFieldException {
        xmlBasedObjectMapper.addXmlObjectMapper(new File("src/test/resources/com.tw.orm/userMapper.xml"));

        ObjectDescriptor objectDescriptor = xmlBasedObjectMapper.getDescriptor(User.class);
        assertThat(objectDescriptor, is(notNullValue()));
        PropertyDescriptor usernameProperty = objectDescriptor.getPropertyDescriptor("username");
        PropertyDescriptor nicknameProperty = objectDescriptor.getPropertyDescriptor("nickname");
        PropertyDescriptor idProperty = objectDescriptor.getPropertyDescriptor("id");
        assertThat(idProperty.getPropertyType(), is(equalTo(String.class)));
        assertThat(idProperty.getFieldName(), is("_id"));
        assertThat(idProperty.getPropertyName(), is("id"));
        assertThat(idProperty.isId(), is(true));
        assertThat(nicknameProperty.getPropertyType(), is(equalTo(String.class)));
        assertThat(usernameProperty.getPropertyType(), is(equalTo(String.class)));


    }
}