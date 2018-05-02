package org.knowm.xchange.exmo.service;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exmo.ExmoExchange;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class ExmoAccountService extends ExmoAccountServiceRaw implements AccountService {
    public ExmoAccountService(ExmoExchange exmoExchange) {
        super(exmoExchange);
    }

    @Override
    public AccountInfo getAccountInfo() throws IOException {
        return new AccountInfo(new Wallet(balances()));
    }

    @Override
    public String requestDepositAddress(Currency currency, String... args) throws IOException {
        return depositAddresses().get(currency.getCurrencyCode());
    }

    @Override
    public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
        throw new NotAvailableFromExchangeException();
    }

    @Override
    public String withdrawFunds(WithdrawFundsParams params) throws IOException {
        throw new NotAvailableFromExchangeException();
    }

    @Override
    public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
        throw new NotAvailableFromExchangeException();
    }
}
