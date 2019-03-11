package org.knowm.xchange.btctrade.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btctrade.BTCTrade;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.utils.CertHelper;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.RestProxyFactory;

public class BTCTradeBaseService extends BaseExchangeService implements BaseService {

  protected final BTCTrade btcTrade;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCTradeBaseService(Exchange exchange) {

    super(exchange);

    ExchangeSpecification exchangeSpecification = exchange.getExchangeSpecification();

    ClientConfig config = getClientConfig();
    // btctrade is using an ssl certificate for 33option.com
    config.setHostnameVerifier(
        CertHelper.createIncorrectHostnameVerifier(
            exchangeSpecification.getHost(),
            "CN=www.33option.com,OU=IT,O=OPTIONFORTUNE TRADE LIMITED,L=KOWLOON,ST=HONGKONG,C=HK"));

    btcTrade =
        RestProxyFactory.createProxy(BTCTrade.class, exchangeSpecification.getSslUri(), config);
  }

  protected long toLong(Object object) {

    final long since;
    if (object instanceof Integer) {
      since = (Integer) object;
    } else if (object instanceof Long) {
      since = (Long) object;
    } else {
      since = Long.parseLong(object.toString());
    }
    return since;
  }
}
