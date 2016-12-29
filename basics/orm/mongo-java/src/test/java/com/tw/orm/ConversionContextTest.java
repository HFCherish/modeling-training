package com.tw.orm;

import com.tw.orm.testObjects.User;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by pzzheng on 12/28/16.
 */
public class ConversionContextTest {

    private ConversionContext conversionContext;
    private XmlBasedObjectMapper objectMapper;

    @Before
    public void setUp() throws SAXException, IOException, XPathExpressionException, ClassNotFoundException, ParserConfigurationException, NoSuchFieldException {
        conversionContext = new ConversionContext();
        objectMapper = new XmlBasedObjectMapper();
        objectMapper.addXmlObjectMapper(new File("src/test/resources/com.tw.orm/userMapper.xml"));
    }

    @Test
    public void should_return_the_obj_if_convert_to_same_type() {
        assertThat(conversionContext.convert("string", String.class), is("string"));
        assertThat(conversionContext.convert(1, Integer.class), is(1));
        assertThat(conversionContext.convert(1.1, Double.class), is(1.1));
        assertThat(conversionContext.convert(true, Boolean.class), is(true));
    }

    @Test
    public void should_convert_from_mongo_document_to_dojo() {
        Document document = new Document("username", "petrina")
                .append("nickname", "xz");
        conversionContext.registerTypeHandler(new ObjectHandler(objectMapper));
        User convertUser = conversionContext.convert(document, User.class);

        assertThat(convertUser.getId(), is(document.getString("_id")));
        assertThat(convertUser.getUsername(), is(document.getString("username")));
        assertThat(convertUser.getNickname(), is(document.getString("nickname")));
    }
}