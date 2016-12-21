import com.tw.ioc.DI;
import com.tw.ioc.Injector;
import com.tw.ioc.Names;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

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
            binder.bind(ToInject.class).annotatedWith(Names.named("first")).to(ToInjectImpl.class);
            binder.bind(ToInject.class).annotatedWith(Names.named("second")).to(ToInjectImpl2.class);
            binder.bind(ToInject.class).annotatedWith(DefaultCase.class).to(ToInjectImpl.class);
        });
    }

    @Test
    public void should_inject_field_with_annotation() {
        WithFieldToInject instance = injector.getInstance(WithFieldToInject.class);
        assertThat(instance.execute(), is("hello petrina"));
        assertThat(instance.execute2(), is("hello petrina2"));
    }

    @Test
    public void should_inject_method_with_annotation() {
        WithMethodToInject instance = injector.getInstance(WithMethodToInject.class);
        assertThat(instance.execute(), is("hello petrina"));
        assertThat(instance.execute2(), is("hello petrina2"));
    }

    @Test
    public void should_able_to_get_self_defined_annotation() {
        try {
            Field toInject = WithFieldToInjectUsingSelfDefinedQualifier.class.getDeclaredField("toInject");
            Field toInject3 = WithFieldToInjectUsingSelfDefinedQualifier.class.getDeclaredField("toInject3");
            assertThat(toInject.isAnnotationPresent(DefaultCase.class), is(true));
            assertThat(toInject.getAnnotation(DefaultCase.class), is(toInject3.getAnnotation(DefaultCase.class)));

            Field toInject2 = WithFieldToInject.class.getDeclaredField("toInject2");
            toInject = WithFieldToInject.class.getDeclaredField("toInject");
            assertThat(toInject.getAnnotation(Named.class), not(toInject2.getAnnotation(Named.class)));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void should_inject_field_with_self_defined_annotation() {
        WithFieldToInjectUsingSelfDefinedQualifier instance = injector.getInstance(WithFieldToInjectUsingSelfDefinedQualifier.class);
        assertThat(instance.execute(), is("hello petrina"));
        assertThat(instance.execute2(), is("hello petrina2"));
    }

    @Qualifier
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DefaultCase {}

    static class WithFieldToInjectUsingSelfDefinedQualifier {
        @Inject
        @DefaultCase
        ToInject toInject;

//        @Inject
//        @DefaultCase
//        ToInject toInject3;

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

}
