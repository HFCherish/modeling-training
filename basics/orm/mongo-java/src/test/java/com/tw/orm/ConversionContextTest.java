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

import static org.hamcrest.CoreMatchers.*;
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
    public void should_convert_from_mongo_document_to_pojo() {
        Document document = new Document("username", "petrina")
                .append("nickname", "xz");
        conversionContext.registerTypeHandler(new ObjectHandler(objectMapper));
        User convertUser = conversionContext.convert(document, User.class);

        assertThat(convertUser.getId(), is(document.getString("_id")));
        assertThat(convertUser.getUsername(), is(document.getString("username")));
        assertThat(convertUser.getNickname(), is(document.getString("nickname")));
        assertThat(convertUser.getPersonLocation(), is(nullValue()));
    }

    @Test
    public void should_convert_from_mongo_document_to_pojo_and_set_property_inherited() throws SAXException, IOException, XPathExpressionException, ClassNotFoundException, ParserConfigurationException, NoSuchFieldException {
        objectMapper.addXmlObjectMapper(new File("src/test/resources/com.tw.orm/locationMapper.xml"));
        Document document = new Document("username", "petrina")
                                .append("nickname", "xz")
                                .append("sex", "female")
                                .append("location", new Document("country", "China")
                                                        .append("city", "Beijing"));
        conversionContext.registerTypeHandler(new ObjectHandler(objectMapper));
        User convertUser = conversionContext.convert(document, User.class);

        assertThat(convertUser.getId(), is(nullValue()));
        assertThat(convertUser.getUsername(), is(document.getString("username")));
        assertThat(convertUser.getNickname(), is(document.getString("nickname")));
        assertThat(convertUser.getSex(), is(notNullValue()));
        assertThat(convertUser.getSex(), is("female"));
        assertThat(convertUser.getPersonLocation(), is(notNullValue()));
        assertThat(convertUser.getPersonLocation().getCountry(), is("China"));
        assertThat(convertUser.getPersonLocation().getCity(), is("Beijing"));
    }

    @Test
    public void should_convert_from_pojo_to_document() {
        User user = new User().setId("someId").setUsername("petrina").setNickname("xz");
        conversionContext.registerTypeHandler(new ObjectHandler(objectMapper));
        Document convertDocument = conversionContext.convert(user, Document.class);

        assertThat(convertDocument.getString("_id"), is(user.getId()));
        assertThat(convertDocument.getString("username"), is(user.getUsername()));
        assertThat(convertDocument.getString("nickname"), is(user.getNickname()));
    }

    @Test
    public void should_convert_from_pojo_to_document_and_process_inherited_properties() {
        User user = new User().setId("someId").setUsername("petrina").setNickname("xz");
        user.setSex("female");
        conversionContext.registerTypeHandler(new ObjectHandler(objectMapper));
        Document convertDocument = conversionContext.convert(user, Document.class);

        assertThat(convertDocument.getString("_id"), is(user.getId()));
        assertThat(convertDocument.getString("username"), is(user.getUsername()));
        assertThat(convertDocument.getString("nickname"), is(user.getNickname()));
        assertThat(convertDocument.getString("sex"), is(user.getSex()));
    }

    @Test
    public void should_convert_from_pojo_to_document_and_add_id_if_not_exists() {
        User user = new User().setUsername("petrina").setNickname("xz");
        conversionContext.registerTypeHandler(new ObjectHandler(objectMapper));
        Document convertDocument = conversionContext.convert(user, Document.class);

        assertThat(convertDocument.get("_id"), is(notNullValue()));
        assertThat(convertDocument.getString("username"), is(user.getUsername()));
        assertThat(convertDocument.getString("nickname"), is(user.getNickname()));
    }

//    @Test
//    public void should_convert_from_collection_to_bsonarray() {
//        List<User> users = asList(new User().setId("1"), new User().setId("2"));
//        conversionContext.registerTypeHandler(new CollectionHandler());
//        BsonArray bsonValues = conversionContext.convert(users, BsonArray.class);
//
//        assertThat(bsonValues.size(), is(2));
//        assertThat(bsonValues.get(0).asDocument().getString("_id"), is(anyOf(equalTo("1"), equalTo("2"))));
//    }

//    @Test
//    public void should_convert_from_pojo_to_document_with_collection() {
//        Person father = new Person().setSons(new Person(), new Person());
//        conversionContext.registerTypeHandler(new ObjectHandler(objectMapper));
//        Document document = conversionContext.convert(father, Document.class);
//
//        assertThat(document.get("sons") instanceof Collection, is(true));
//        assertThat(((List)document.get("sons")).size(), is(2));
//        System.out.println(((List) document.get("sons")).get(0).toString() + "******");
//        assertThat(((List) document.get("sons")).get(0) instanceof Document, is(true));
//    }

//    @Test
//    public void should_convert_from_document_to_pojo_with_collection() {
//        Document document = new Document("sons", asList(new Document("sex", "female"), new Document("sex", "male")));
//        conversionContext.registerTypeHandler(new ObjectHandler(objectMapper));
//        Person person = conversionContext.convert(document, Person.class);
//
//        assertThat(person.getSons().size(), is(2));
//        assertThat(person.getSex(), is(anyOf(equalTo("female"), equalTo("maler"))));
//    }
}