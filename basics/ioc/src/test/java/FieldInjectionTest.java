import com.tw.ioc.DI;
import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Scope;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by pzzheng on 12/20/16.
 */
public class FieldInjectionTest {

    @Test
    public void should_inject_fields_for_a_instance() {
        WithFieldToInject instance = new WithFieldToInject();
        DI.createInjector(new Configurations()).injectMembers(instance);
        assertThat(instance.execute(), is("hello petrina"));
    }

    static class WithFieldToInject {
        @Inject
        ToInject toInject;

        public String execute() {
            return toInject.sayHello();
        }
    }
}
