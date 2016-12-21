import com.tw.ioc.DI;
import com.tw.ioc.Injector;
import com.tw.ioc.Scopes;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by pzzheng on 12/19/16.
 */
public class ClassInjectionTest {
    @Test
    public void should_can_bind_to_implementation_class() {
        Injector injector = DI.createInjector(binder -> binder.bind(ToInject.class).to(ToInjectImpl.class));

        ToInject instance1 = injector.getInstance(ToInject.class);
        ToInject instance2 = injector.getInstance(ToInject.class);
        assertThat(instance1.sayHello(), is("hello petrina"));
        assertThat(instance1.equals(instance2), is(false));
    }

    @Test
    public void should_can_bind_to_instance() {
        Injector injector = DI.createInjector(binder -> binder.bind(ToInject.class).toInstance(new ToInjectImpl()));

        ToInject instance1 = injector.getInstance(ToInject.class);
        ToInject instance2 = injector.getInstance(ToInject.class);
        assertThat(instance1.sayHello(), is("hello petrina"));
        assertThat(instance1.equals(instance2), is(true));
    }

    @Test
    public void should_can_bind_in_singleton_scope() {
        Injector injector = DI.createInjector(binder -> binder.bind(ToInject.class).to(ToInjectImpl.class).in(Scopes.SINGLETON));

        ToInject instance1 = injector.getInstance(ToInject.class);
        ToInject instance2 = injector.getInstance(ToInject.class);
        assertThat(instance1.sayHello(), is("hello petrina"));
        assertThat(instance1.equals(instance2), is(true));
    }
}