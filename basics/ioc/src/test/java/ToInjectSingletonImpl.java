/**
 * Created by pzzheng on 12/19/16.
 */
public class ToInjectSingletonImpl implements ToInjectSingleton {

    public static final String HELLO_PETRINA_SINGLETON = "hello petrina singleton";

    @Override
    public String sayHello() {
        return HELLO_PETRINA_SINGLETON;
    }
}
