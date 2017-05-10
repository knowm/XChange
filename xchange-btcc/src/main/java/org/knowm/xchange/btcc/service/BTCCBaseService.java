package org.knowm.xchange.btcc.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcc.BTCC;
import org.knowm.xchange.btcc.BTCCExchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

import java.net.URI;

public class BTCCBaseService<T extends BTCC> extends BaseExchangeService implements BaseService {

    protected final T btcc;

    protected BTCCBaseService(Exchange exchange, Class<T> type) {
        super(exchange);
        this.btcc = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getExchangeSpecificParametersItem(BTCCExchange.DATA_API_URI_KEY).toString());
    }
}
