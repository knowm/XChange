package org.knowm.xchange.tradeogre.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.tradeogre.dto.account.TradeOgreBalance;

public class TradeOgreAccountServiceRaw extends TradeOgreBaseService {

  protected TradeOgreAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<Balance> getTradeOgreBalances() throws IOException {
    String userPwd =
        exchange.getExchangeSpecification().getApiKey()
            + ":"
            + exchange.getExchangeSpecification().getSecretKey();
    String encoded = "Basic " + new String(Base64.getEncoder().encode(userPwd.getBytes()));

    return tradeOgre.getBalances(encoded).getBalances().entrySet().stream()
        .map(
            entry ->
                new Balance.Builder()
                    .total(entry.getValue())
                    .currency(new Currency(entry.getKey()))
                    .build())
        .collect(Collectors.toList());
  }

  public TradeOgreBalance getTradeOgreBalance(Currency currency) throws IOException {
    String userPwd =
        exchange.getExchangeSpecification().getApiKey()
            + ":"
            + exchange.getExchangeSpecification().getSecretKey();
    String encoded = "Basic " + new String(Base64.getEncoder().encode(userPwd.getBytes()));
    return tradeOgre.getBalance(encoded, currency.toString().toUpperCase());
  }
}
