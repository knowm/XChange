package com.xeiam.xchange.proxy;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.utils.HttpTemplate;
import org.codehaus.jackson.map.ObjectMapper;

import java.lang.reflect.Proxy;

/**
 * @author Matija Mazi <br/>
 */
public class RestProxyFactory {

  /**
   * Create a proxy implementation of restInterface.
   *
   * The interface must be annotated with jax-rs annotations. @Path, @GET, @POST, @QueryParam, @FormParam currently partially supported.
   *
   * @param restInterface The interface to implment.
   * @param <I> The interface to implement.
   * @return a proxy implementation of restInterface.
   */
  public static <I> I createProxy(Class<I> restInterface, HttpTemplate httpTemplate, ExchangeSpecification exchangeSpecification, ObjectMapper mapper) {
    Object proxy = Proxy.newProxyInstance(restInterface.getClassLoader(), new Class[]{restInterface}, new RestInvocationHandler(httpTemplate, exchangeSpecification, mapper, restInterface));
    //noinspection unchecked
    return (I) proxy;
  }
}
