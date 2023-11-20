package org.knowm.xchange.coinbasepro;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import org.junit.Test;
import org.knowm.xchange.client.ProxyConfig;
import org.knowm.xchange.coinbasepro.service.CoinbaseProBaseService;
import si.mazi.rescu.BodyLoggingRestInvocationHandler;
import si.mazi.rescu.CustomRestProxyFactoryImpl;

public class CoinbaseProProxyTest extends CoinbaseProExchangeWiremock{

  static {
    ProxyConfig.getInstance().setRestProxyFactoryClass(CustomRestProxyFactoryImpl.class);
  }

  @Test
  public void testProxyFactory()
      throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    CoinbaseProBaseService service = ((CoinbaseProBaseService) exchange.getMarketDataService());
    assertThat(Proxy.getInvocationHandler(service.getCoinbaseProExchangeRestProxy())).isInstanceOf(BodyLoggingRestInvocationHandler.class);
  }
}
