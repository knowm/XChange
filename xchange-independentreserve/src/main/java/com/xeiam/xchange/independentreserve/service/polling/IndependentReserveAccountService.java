package com.xeiam.xchange.independentreserve.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.independentreserve.IndependentReserveAdapters;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Author: Kamil Zbikowski
 * Date: 4/10/15
 */
public class IndependentReserveAccountService extends IndependentReserveAccountServiceRaw implements PollingAccountService {

    /**
     * Constructor
     *
     * @param exchange
     */
    public IndependentReserveAccountService(Exchange exchange) {
        super(exchange);
    }

    @Override
    public AccountInfo getAccountInfo() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        return IndependentReserveAdapters.adaptAccountInfo(getIndependentReserveBalance(), exchange.getExchangeSpecification().getUserName());
    }

    @Override
    public String withdrawFunds(String currency, BigDecimal amount, String address) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        return null;
    }

    @Override
    public String requestDepositAddress(String currency, String... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        return null;
    }

    @Override
    public List<CurrencyPair> getExchangeSymbols() throws IOException {
        return null;
    }
}
