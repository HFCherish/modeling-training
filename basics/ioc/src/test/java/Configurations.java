import com.tw.ioc.binder.Binder;
import com.tw.ioc.configuration.Configuration;

/**
 * Created by pzzheng on 12/20/16.
 */
public class Configurations implements Configuration {
    @Override
    public void configure(Binder binder) {
        binder.bind(ToInject.class).to(ToInjectImpl.class);
    }
}
