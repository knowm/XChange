package org.knowm.xchange.bybit.service;

import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.service.account.AccountService;

public class BybitAccountService extends BybitAccountServiceRaw implements AccountService {

    public BybitAccountService(BybitExchange exchange) {
        super(exchange);
    }
}
