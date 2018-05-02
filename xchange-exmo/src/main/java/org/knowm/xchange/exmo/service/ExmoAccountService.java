package org.knowm.xchange.exmo.service;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.exmo.ExmoExchange;
import org.knowm.xchange.service.account.AccountService;

import java.io.IOException;
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
}
