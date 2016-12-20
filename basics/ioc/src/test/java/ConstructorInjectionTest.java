import com.tw.ioc.DI;
import org.junit.Test;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by pzzheng on 12/20/16.
 */
public class ConstructorInjectionTest {

    @Test
    public void should_inject_construct_parameter() {
        WithToInjectConstructor instance = DI.createInjector(binder -> binder.bind(ToInject.class).to(ToInjectImpl.class)).getInstance(WithToInjectConstructor.class);
        assertThat(instance.execute(), is("hello petrina"));
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

}
