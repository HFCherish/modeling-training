import com.tw.ioc.DI;
import com.tw.ioc.Injector;
import com.tw.ioc.Names;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Named;

import static org.hamcrest.CoreMatchers.is;
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
