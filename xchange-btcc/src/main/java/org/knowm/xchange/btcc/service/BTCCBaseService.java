package org.knowm.xchange.btcc.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcc.BTCC;
import org.knowm.xchange.btcc.BTCCExchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class BTCCBaseService<T extends BTCC> extends BaseExchangeService implements BaseService {

  protected final T btcc;

  protected BTCCBaseService(Exchange exchange, Class<T> type) {
    super(exchange);
    final String baseUrl =
        (String)
            exchange
                .getExchangeSpecification()
                .getExchangeSpecificParametersItem(BTCCExchange.DATA_API_URI_KEY);
    this.btcc =
        ExchangeRestProxyBuilder.forInterface(type, exchange.getExchangeSpecification())
            .baseUrl(baseUrl)
            .build();
  }
}
