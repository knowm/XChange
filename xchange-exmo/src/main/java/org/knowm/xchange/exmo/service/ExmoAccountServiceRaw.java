package org.knowm.xchange.exmo.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.Balance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExmoAccountServiceRaw extends BaseExmoService {
    protected ExmoAccountServiceRaw(Exchange exchange) {
        super(exchange);
    }

    public Map<String, String> depositAddresses() {
        return exmo.depositAddress(signatureCreator, apiKey, exchange.getNonceFactory());
    }

    public List<Balance> balances() {
        Map map = exmo.userInfo(signatureCreator, apiKey, exchange.getNonceFactory());

        Map<String, String> balances = (Map<String, String>) map.get("balances");
        Map<String, String> reserved = (Map<String, String>) map.get("reserved");

        List<Balance> results = new ArrayList<>();
        for (String ccy : balances.keySet()) {
            results.add(ExmoAdapters.adaptBalance(balances, reserved, ccy));
        }

        return results;
    }
}
