package org.knowm.xchange.ftx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.account.AccountService;

public class FtxAccountService extends FtxAccountServiceRaw implements AccountService {

    public FtxAccountService(Exchange exchange) {
        super(exchange);
    }
}
