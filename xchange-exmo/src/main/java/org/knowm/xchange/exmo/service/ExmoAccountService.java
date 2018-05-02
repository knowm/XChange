package org.knowm.xchange.exmo.service;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exmo.ExmoExchange;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExmoAccountService extends ExmoAccountServiceRaw implements AccountService {
    public ExmoAccountService(ExmoExchange exmoExchange) {
        super(exmoExchange);
    }

    @Override
    public AccountInfo getAccountInfo() throws IOException {
        Map map = exmo.userInfo(signatureCreator, apiKey, exchange.getNonceFactory());

        Map<String, String> balances = (Map<String, String>) map.get("balances");
        Map<String, String> reserved = (Map<String, String>) map.get("reserved");

        List<Balance> results = new ArrayList<>();
        for (String ccy : balances.keySet()) {
            Balance balance = ExmoAdapters.adaptBalance(balances, reserved, ccy);
            results.add(balance);
        }

        return new AccountInfo(new Wallet(results));
    }

    @Override
    public String requestDepositAddress(Currency currency, String... args) throws IOException {
        Map<String, String> map = exmo.depositAddress(signatureCreator, apiKey, exchange.getNonceFactory());
        return map.get(currency.getCurrencyCode());
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
