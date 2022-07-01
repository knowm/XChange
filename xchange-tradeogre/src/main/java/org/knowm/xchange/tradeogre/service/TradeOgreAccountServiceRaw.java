package org.knowm.xchange.tradeogre.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.tradeogre.TradeOgreExchange;
import org.knowm.xchange.tradeogre.dto.account.TradeOgreBalance;

public class TradeOgreAccountServiceRaw extends TradeOgreBaseService {

  protected TradeOgreAccountServiceRaw(TradeOgreExchange exchange) {
    super(exchange);
  }

  public List<Balance> getTradeOgreBalances() throws IOException {

    return tradeOgre.getBalances(base64UserPwd).getBalances().entrySet().stream()
        .map(
            entry ->
                new Balance.Builder()
                    .total(entry.getValue())
                    .currency(new Currency(entry.getKey()))
                    .build())
        .collect(Collectors.toList());
  }

  public TradeOgreBalance getTradeOgreBalance(Currency currency) throws IOException {
    return tradeOgre.getBalance(base64UserPwd, currency.toString().toUpperCase());
  }
}
