import com.tw.ioc.DI;
import com.tw.ioc.Injector;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by pzzheng on 12/19/16.
 */
public class ToInjectTest {
    @Test
    public void should_can_inject_class() {
        Injector injector = DI.createInjector(binder -> binder.bind(ToInject.class).to(ToInjectImpl.class));

        assertThat(injector.getInstance(ToInject.class).sayHello(), is("hello petrina"));
    }
}