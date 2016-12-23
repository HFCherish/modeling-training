import com.tw.ioc.DI;
import org.junit.Test;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by pzzheng on 12/20/16.
 */
public class ConstructorInjectionTest {

    @Test
    public void should_inject_construct_parameter() {
        WithToInjectConstructor instance = DI.createInjector(new Configurations()).getInstance(WithToInjectConstructor.class);
        assertThat(instance.execute(), is("hello petrina"));
    }

    @Test
    public void should_only_has_one_constructor_with_inject_annotation() {
        InvalidWithToInjectConstructor instance = DI.createInjector(new Configurations()).getInstance(InvalidWithToInjectConstructor.class);
        assertThat(instance, is(nullValue()));
//        assertThat(instance.execute(), is("hello petrina"));
    }

    static class WithToInjectConstructor {
        private final ToInject toInject;

        @Inject
        public WithToInjectConstructor(ToInject toInject) {
            this.toInject = toInject;
        }

        public String execute() {
            return toInject.sayHello();
        }
    }

    static class InvalidWithToInjectConstructor {
        private final ToInject toInject;

        @Inject
        public InvalidWithToInjectConstructor(ToInject toInject) {
            this.toInject = toInject;
        }

        @Inject
        public InvalidWithToInjectConstructor(ToInject toInject, ToInject toInject1) {
            this.toInject = toInject;
        }

        public String execute() {
            return toInject.sayHello();
        }
    }

}
