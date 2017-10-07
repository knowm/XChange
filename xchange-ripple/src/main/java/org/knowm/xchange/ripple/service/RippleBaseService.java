package org.knowm.xchange.ripple.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ripple.RippleAuthenticated;
import org.knowm.xchange.ripple.RipplePublic;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.RestProxyFactory;

public class RippleBaseService extends BaseExchangeService implements BaseService {

  protected final RipplePublic ripplePublic;
  protected final RippleAuthenticated rippleAuthenticated;

  public RippleBaseService(final Exchange exchange) {
    super(exchange);
    final String uri;

    // allow HTTP connect- and read-timeout to be set per exchange
    ClientConfig rescuConfig = new ClientConfig(); // default rescu config
    int customHttpConnTimeout = exchange.getExchangeSpecification().getHttpConnTimeout();
    if (customHttpConnTimeout > 0) {
      rescuConfig.setHttpConnTimeout(customHttpConnTimeout);
    }
    int customHttpReadTimeout = exchange.getExchangeSpecification().getHttpReadTimeout();
    if (customHttpReadTimeout > 0) {
      rescuConfig.setHttpReadTimeout(customHttpReadTimeout);
    }

    if (exchange.getExchangeSpecification().getSslUri() != null && exchange.getExchangeSpecification().getSslUri().length() > 0) {
      // by default use an SSL encrypted connection if it is configured 
      uri = exchange.getExchangeSpecification().getSslUri();
    } else if (exchange.getExchangeSpecification().getPlainTextUri() != null && exchange.getExchangeSpecification().getPlainTextUri().length() > 0) {
      // otherwise try a plain text connection
      uri = exchange.getExchangeSpecification().getPlainTextUri();
    } else {
      throw new IllegalStateException("either SSL or plain text URI must be specified");
    }
    ripplePublic = RestProxyFactory.createProxy(RipplePublic.class, uri, rescuConfig);
    rippleAuthenticated = RestProxyFactory.createProxy(RippleAuthenticated.class, uri, rescuConfig);
  }
}
