import com.tw.ioc.DI;
import com.tw.ioc.Injector;
import com.tw.ioc.Names;
import org.junit.Before;
import org.junit.Test;
import sun.reflect.annotation.AnnotationParser;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Qualifier;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by pzzheng on 12/20/16.
 */
public class QualifierInjectionTest {

    private Injector injector;

    @Before
    public void setUp() {
        injector = DI.createInjector(binder -> {
//            binder.bind(ToInject.class).annotatedWith(Names.named("first")).to(ToInjectImpl.class);
//            binder.bind(ToInject.class).annotatedWith(Names.named("second")).to(ToInjectImpl2.class);
//            binder.bind(ToInject.class).annotatedWith(DefaultCase.class).to(ToInjectImpl.class);
            binder.bind(ToInject.class).annotatedWith(AnnotationParser.annotationForMap(Named.class, Collections.singletonMap("value", "first"))).to(ToInjectImpl.class);
            binder.bind(ToInject.class).annotatedWith(AnnotationParser.annotationForMap(Named.class, Collections.singletonMap("value", "second"))).to(ToInjectImpl2.class);
            binder.bind(ToInject.class).annotatedWith(AnnotationParser.annotationForMap(DefaultCase.class, Collections.emptyMap())).to(ToInjectImpl.class);
        });
    }

    @Test
    public void should_inject_field_with_annotation() {
        WithFieldToInject instance = injector.getInstance(WithFieldToInject.class);
        assertThat(instance.execute(), is(ToInjectImpl.HELLO_PETRINA));
        assertThat(instance.execute2(), is(ToInjectImpl2.HELLO_PETRINA2));
    }

    @Test
    public void should_inject_method_with_annotation() {
        WithMethodToInject instance = injector.getInstance(WithMethodToInject.class);
        assertThat(instance.execute(), is(ToInjectImpl.HELLO_PETRINA));
        assertThat(instance.execute2(), is(ToInjectImpl2.HELLO_PETRINA2));
    }

    @Test
    public void should_inject_field_with_self_defined_annotation() {
        WithFieldToInjectUsingSelfDefinedQualifier instance = injector.getInstance(WithFieldToInjectUsingSelfDefinedQualifier.class);
        assertThat(instance.execute(), is(ToInjectImpl.HELLO_PETRINA));
        assertThat(instance.execute2(), is(ToInjectImpl2.HELLO_PETRINA2));
    }

    @Test
    public void should_inject_method_parameter_with_self_defined_annotation() {
        WithMethodToInjectUsingSelfDefinedAnnotation instance = injector.getInstance(WithMethodToInjectUsingSelfDefinedAnnotation.class);
        assertThat(instance.execute(), is(ToInjectImpl.HELLO_PETRINA));
        assertThat(instance.execute2(), is(ToInjectImpl2.HELLO_PETRINA2));
    }

    @Test
    public void should_able_to_get_self_defined_annotation() throws NoSuchFieldException {
            Field toInject = WithFieldToInjectUsingSelfDefinedQualifier.class.getDeclaredField("toInject");
            Field toInject3 = WithFieldToInjectUsingSelfDefinedQualifier.class.getDeclaredField("toInject3");
            assertThat(toInject.isAnnotationPresent(DefaultCase.class), is(true));
            assertThat(toInject.getAnnotation(DefaultCase.class), is(toInject3.getAnnotation(DefaultCase.class)));

            Field toInject2 = WithFieldToInject.class.getDeclaredField("toInject2");
            toInject = WithFieldToInject.class.getDeclaredField("toInject");
            assertThat(toInject.getAnnotation(Named.class), not(toInject2.getAnnotation(Named.class)));
    }

    @Test
    public void annotation_instantiate_test() {
        Annotation firstNameAnnotation = AnnotationParser.annotationForMap(Named.class, Collections.singletonMap("value", "first"));
        Annotation firstNameAnnotation1 = AnnotationParser.annotationForMap(Named.class, Collections.singletonMap("value", "first"));
        Annotation secondNameAnnotation = AnnotationParser.annotationForMap(Named.class, Collections.singletonMap("value", "second"));
        assertThat(firstNameAnnotation, is(firstNameAnnotation1));
        assertThat(firstNameAnnotation, not(secondNameAnnotation));
        assertThat(firstNameAnnotation instanceof Named, is(true));

        Annotation defaultCaseAnnotation = AnnotationParser.annotationForMap(DefaultCase.class, Collections.emptyMap());
        Annotation defaultCaseAnnotation1 = AnnotationParser.annotationForMap(DefaultCase.class, Collections.emptyMap());
        assertThat(defaultCaseAnnotation instanceof DefaultCase, is(true));
        assertThat(defaultCaseAnnotation, is(defaultCaseAnnotation1));
    }

    @Qualifier
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DefaultCase {}

    static class WithFieldToInjectUsingSelfDefinedQualifier {
        @Inject
        @DefaultCase
        ToInject toInject;

        @Inject
        @DefaultCase
        ToInject toInject3;

        @Inject
        @Named("second")
        ToInject toInject2;


        public String execute() {
            return toInject.sayHello();
        }

        public String execute2() {
            return toInject2.sayHello();
        }
    }

    static class WithFieldToInject {
        @Inject
        @Named("first")
        ToInject toInject;

        @Inject
        @Named("second")
        ToInject toInject2;


        public String execute() {
            return toInject.sayHello();
        }

        public String execute2() {
            return toInject2.sayHello();
        }
    }

    static class WithMethodToInject {
        ToInject toInject;
        ToInject toInject2;

        @Inject
        public void setToInject(@Named("first")ToInject toInject, @Named("second") ToInject toInject2) {
            this.toInject = toInject;
            this.toInject2 = toInject2;
        }

        public String execute() {
            return toInject.sayHello();
        }

        public String execute2() {
            return toInject2.sayHello();
        }
    }

    static class WithMethodToInjectUsingSelfDefinedAnnotation {
        ToInject toInject;
        ToInject toInject2;

        @Inject
        public void setToInject(@DefaultCase ToInject toInject, @Named("second") ToInject toInject2) {
            this.toInject = toInject;
            this.toInject2 = toInject2;
        }

        public String execute() {
            return toInject.sayHello();
        }

        public String execute2() {
            return toInject2.sayHello();
        }
    }

}
