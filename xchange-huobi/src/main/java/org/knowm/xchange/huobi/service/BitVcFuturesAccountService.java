package org.knowm.xchange.huobi.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.huobi.BitVcFuturesAdapter;
import org.knowm.xchange.huobi.dto.account.BitVcFuturesAccountInfo;
import org.knowm.xchange.service.account.AccountService;

import java.io.IOException;
import java.math.BigDecimal;

public class BitVcFuturesAccountService extends BitVcFuturesServiceRaw implements AccountService {
    public BitVcFuturesAccountService(final Exchange exchange) {
        super(exchange);
    }

    @Override
    public AccountInfo getAccountInfo() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        final BitVcFuturesAccountInfo accountInfo = bitvc.balance(accessKey, 1, requestTimestamp(), digest);
        return BitVcFuturesAdapter.adaptAccountInfo(accountInfo);
    }

    @Override
    public String withdrawFunds(final Currency currency, final BigDecimal bigDecimal, final String s) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        return null;
    }

    @Override
    public String requestDepositAddress(final Currency currency, final String... strings) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        return null;
    }
}
