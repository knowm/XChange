package org.knowm.xchange.gateio.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.gateio.GateioExchangeWiremock;
import org.knowm.xchange.gateio.config.Config;
import si.mazi.rescu.BodyLoggingRestInvocationHandler;
import si.mazi.rescu.CustomRestProxyFactoryImpl;

/**
 * Contains the example of overriding of RestProxyFactory for exchange for some specific logic
 */
class GateioBaseServiceTest extends GateioExchangeWiremock {

  // set custom proxy factory before creating the exchange
  static {
    Config.getInstance().setRestProxyFactoryClass(CustomRestProxyFactoryImpl.class);
  }


  @Test
  void correct_proxy_factory() {
    GateioBaseService service = ((GateioBaseService) exchange.getAccountService());
    assertThat(Proxy.getInvocationHandler(service.gateio) instanceof BodyLoggingRestInvocationHandler).isTrue();
  }


}