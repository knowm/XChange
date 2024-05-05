package si.mazi.rescu;

/**
 * The implementation of {@link IRestProxyFactory} that instantiates {@link BodyLoggingRestInvocationHandler}
 */
public class CustomRestProxyFactoryImpl implements IRestProxyFactory {

  @Override
  public <I> I createProxy(Class<I> restInterface, String baseUrl, ClientConfig config, Interceptor... interceptors) {
    return RestProxyFactory.createProxy(restInterface, RestProxyFactory.wrap(new BodyLoggingRestInvocationHandler(restInterface, baseUrl, config), interceptors));
  }

  @Override
  public <I> I createProxy(Class<I> restInterface, String baseUrl) {
    return createProxy(restInterface, baseUrl, null);
  }
}
