package org.knowm.xchange.bl3p.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bl3p.Bl3p;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

public class Bl3pBaseService extends BaseExchangeService implements BaseService {

    protected final Bl3p bl3p;

    public Bl3pBaseService(Exchange exchange) {
        super(exchange);

        this.bl3p = RestProxyFactory.createProxy(Bl3p.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    }

}
