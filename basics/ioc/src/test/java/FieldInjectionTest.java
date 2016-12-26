import com.tw.ioc.DI;
import org.junit.Test;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by pzzheng on 12/20/16.
 */
public class FieldInjectionTest {

    @Test
    public void should_inject_fields_for_a_instance() {
        WithFieldToInject instance = new WithFieldToInject();
        DI.createInjector(binder -> binder.bind(ToInject.class).to(ToInjectImpl.class)).injectMembers(instance);
        assertThat(instance.execute(), is(ToInjectImpl.HELLO_PETRINA));
    }

    @Test
    public void should_inject_fields_when_get_instance() {
        WithFieldToInject instance = DI.createInjector(binder -> binder.bind(ToInject.class).to(ToInjectImpl.class)).getInstance(WithFieldToInject.class);
        assertThat(instance.execute(), is(ToInjectImpl.HELLO_PETRINA));
    }

    static class WithFieldToInject {
        @Inject
        ToInject toInject;

        public String execute() {
            return toInject.sayHello();
        }
    }
}
