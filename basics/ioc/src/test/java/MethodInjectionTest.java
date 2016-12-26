import com.tw.ioc.DI;
import org.junit.Test;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by pzzheng on 12/20/16.
 */
public class MethodInjectionTest {

    @Test
    public void should_inject_method_parameters_for_a_instance() {
        WithMethodToInject instance = DI.createInjector(binder -> binder.bind(ToInject.class).to(ToInjectImpl.class)).getInstance(WithMethodToInject.class);
        assertThat(instance.execute(), is(ToInjectImpl.HELLO_PETRINA));
    }

    static class WithMethodToInject {
        ToInject toInject;

        @Inject
        public void setToInject(ToInject toInject) {
            this.toInject = toInject;
        }

        public String execute() {
            return toInject.sayHello();
        }
    }
}
