import com.tw.ioc.DI;
import com.tw.ioc.Injector;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by pzzheng on 12/20/16.
 */
public class ToInjectSingletonTest {
    @Test
    public void should_can_bind_class_using_singleton_annotation() {
        Injector injector = DI.createInjector(binder -> binder.bind(ToInjectSingleton.class).to(ToInjectSingletonImpl.class));

        ToInjectSingleton instance1 = injector.getInstance(ToInjectSingleton.class);
        ToInjectSingleton instance2 = injector.getInstance(ToInjectSingleton.class);
        assertThat(instance1.sayHello(), is("hello petrina"));
        assertThat(instance1.equals(instance2), is(true));
    }
}