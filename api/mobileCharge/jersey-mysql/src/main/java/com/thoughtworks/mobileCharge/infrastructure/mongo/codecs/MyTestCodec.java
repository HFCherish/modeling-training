package com.thoughtworks.mobileCharge.infrastructure.mongo.codecs;

import com.thoughtworks.mobileCharge.domain.test.MyCodecTest;
import org.bson.*;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by pzzheng on 12/8/16.
 */
public class MyTestCodec implements CollectibleCodec<MyCodecTest> {
    private Codec<Document> documentCodec;

    public MyTestCodec(Codec<Document> codec) {
        this.documentCodec = codec;
    }

    @Override
    public MyCodecTest generateIdIfAbsentFromDocument(MyCodecTest document) {
        return documentHasId(document) ? document : document.withNewId();
    }

    @Override
    public boolean documentHasId(MyCodecTest document) {
        return document.getId() != null && !document.getId().trim().isEmpty();
    }

    @Override
    public BsonValue getDocumentId(MyCodecTest document) {
        if (!documentHasId(document)) {
            throw new IllegalStateException("the document does not contain an _id");
        }
        return new BsonString(document.getId());
    }

    @Override
    public MyCodecTest decode(BsonReader reader, DecoderContext decoderContext) {
        Document document = documentCodec.decode(reader, decoderContext);
        try {
            Constructor<MyCodecTest> constructor = MyCodecTest.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            MyCodecTest myCodecTest = constructor.newInstance(new Object[0]);

            Field id = MyCodecTest.class.getDeclaredField("id");
            if (!id.isAccessible()) id.setAccessible(true);
            id.set(myCodecTest, document.get("_id").toString());

            Field name = MyCodecTest.class.getDeclaredField("name");
            if (!name.isAccessible()) name.setAccessible(true);
            name.set(myCodecTest, document.get("name").toString());

            return myCodecTest;
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("does not has an empty constructor");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void encode(BsonWriter writer, MyCodecTest value, EncoderContext encoderContext) {
        Document document = new Document();
        String id = value.getId();
        String name = value.getName();

        if(id!=null) {
            document.put("_id", id);
        }

        if(name!=null) {
            document.put("name", name);
        }

        documentCodec.encode(writer, document, encoderContext);
    }

    @Override
    public Class<MyCodecTest> getEncoderClass() {
        return MyCodecTest.class;
    }
}
