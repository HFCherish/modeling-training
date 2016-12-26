/**
 * Created by pzzheng on 12/19/16.
 */
public class ToInjectImpl implements ToInject {

    public static final String HELLO_PETRINA = "hello petrina";

    @Override
    public String sayHello() {
        return HELLO_PETRINA;
    }
}
