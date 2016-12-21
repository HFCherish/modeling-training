import com.tw.ioc.DI;
import com.tw.ioc.Names;
import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Named;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by pzzheng on 12/20/16.
 */
public class QualifierInjectionTest {
    @Test
    public void should_inject_with_annotation() {
//        WithFieldToInject instance = DI.createInjector(binder -> {
//            binder.bind(ToInject.class).annotatedWith(Names.named("first")).to(ToInjectImpl.class);
//            binder.bind(ToInject.class).annotatedWith(Names.named("second")).to(ToInjectImpl2.class);
//        }).getInstance(WithFieldToInject.class);
//        assertThat(instance.execute(), is("hello petrina"));
//        assertThat(instance.execute2(), is("hello petrina2"));
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

}
