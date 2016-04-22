package org.knowm.xchange.ripple.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ripple.RippleAuthenticated;
import org.knowm.xchange.ripple.RipplePublic;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

import si.mazi.rescu.RestProxyFactory;

public class RippleBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final RipplePublic ripplePublic;
  protected final RippleAuthenticated rippleAuthenticated;

  public RippleBasePollingService(final Exchange exchange) {
    super(exchange);
    final String uri;
    if (exchange.getExchangeSpecification().getSslUri() != null && exchange.getExchangeSpecification().getSslUri().length() > 0) {
      // by default use an SSL encrypted connection if it is configured 
      uri = exchange.getExchangeSpecification().getSslUri();
    } else if (exchange.getExchangeSpecification().getPlainTextUri() != null && exchange.getExchangeSpecification().getPlainTextUri().length() > 0) {
      // otherwise try a plain text connection
      uri = exchange.getExchangeSpecification().getPlainTextUri();
    } else {
      throw new IllegalStateException("either SSL or plain text URI must be specified");
    }
    ripplePublic = RestProxyFactory.createProxy(RipplePublic.class, uri);
    rippleAuthenticated = RestProxyFactory.createProxy(RippleAuthenticated.class, uri);
  }
}
