package com.xeiam.xchange.btctrade.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btctrade.BTCTrade;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

import com.xeiam.xchange.utils.IncorrectHostnameVerifier;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.RestProxyFactory;

public class BTCTradeBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final BTCTrade btcTrade;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCTradeBasePollingService(Exchange exchange) {

    super(exchange);

    ExchangeSpecification exchangeSpecification = exchange.getExchangeSpecification();

    ClientConfig config = new ClientConfig();
    // btctrade is using an ssl certificate for 33option.com
    config.setHostnameVerifier(new IncorrectHostnameVerifier(exchangeSpecification.getHost(),
        "CN=www.33option.com,OU=IT,O=OPTIONFORTUNE TRADE LIMITED,L=KOWLOON,ST=HONGKONG,C=HK"));

    btcTrade = RestProxyFactory.createProxy(BTCTrade.class, exchangeSpecification.getSslUri(), config);
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
