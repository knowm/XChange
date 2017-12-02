package org.knowm.xchange.liqui.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.liqui.LiquiAdapters;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class LiquiAccountService extends LiquiAccountServiceRaw implements AccountService {

    public LiquiAccountService(final Exchange exchange) {
        super(exchange);
    }

    @Override
    public AccountInfo getAccountInfo() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        return LiquiAdapters.adaptAccountInfo(getAccountInfoRaw());
    }

    @Override
    public String withdrawFunds(final Currency currency, final BigDecimal amount, final String address) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        throw new NotAvailableFromExchangeException();
    }

    @Override
    public String withdrawFunds(final WithdrawFundsParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        throw new NotAvailableFromExchangeException();
    }

    @Override
    public String requestDepositAddress(final Currency currency, final String... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        throw new NotAvailableFromExchangeException();
    }

    @Override
    public TradeHistoryParams createFundingHistoryParams() {
        throw new NotAvailableFromExchangeException();
    }

    @Override
    public List<FundingRecord> getFundingHistory(final TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        throw new NotAvailableFromExchangeException();
    }
}
