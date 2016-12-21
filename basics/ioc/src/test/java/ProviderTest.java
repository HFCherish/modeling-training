import com.tw.ioc.DI;
import com.tw.ioc.Injector;
import com.tw.ioc.Provider;
import com.tw.ioc.Scopes;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by pzzheng on 12/19/16.
 */
public class ProviderTest {
    @Test
    public void should_can_bind_to_provider() {
        Injector injector = DI.createInjector(binder -> binder.bind(ToInject.class).toProvider(ToInjectProvider.class));

        ToInject instance1 = injector.getInstance(ToInject.class);
        assertThat(instance1.sayHello(), is("hello petrina"));
    }

    static class ToInjectProvider implements Provider<ToInject> {
        @Override
        public ToInject get() {
            return new ToInjectImpl();
        }
    }
}