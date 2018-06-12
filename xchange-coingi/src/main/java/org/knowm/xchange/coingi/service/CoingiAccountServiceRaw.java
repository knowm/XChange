package org.knowm.xchange.coingi.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coingi.CoingiAuthenticated;
import org.knowm.xchange.coingi.dto.account.CoingiBalances;
import org.knowm.xchange.coingi.dto.account.TransactionList;
import org.knowm.xchange.coingi.dto.account.WithdrawalResponse;
import org.knowm.xchange.coingi.dto.request.BalanceRequest;
import org.knowm.xchange.coingi.dto.request.TransactionHistoryRequest;
import org.knowm.xchange.coingi.dto.request.WithdrawalRequest;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.RestProxyFactory;

public class CoingiAccountServiceRaw extends CoingiBaseService {
  private final CoingiAuthenticated coingiAuthenticated;

  protected CoingiAccountServiceRaw(Exchange exchange) {
    super(exchange);
    ClientConfig clientConfig = getClientConfig();
    this.coingiAuthenticated =
        RestProxyFactory.createProxy(
            CoingiAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            clientConfig);
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        CoingiDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getUserName(),
            apiKey);
  }

  public CoingiBalances getCoingiBalance() throws IOException {
    BalanceRequest balanceRequest = new BalanceRequest();
    // Currency list:
    // https://github.com/Coingi/exchange-java-client/blob/master/src/main/java/com/coingi/exchange/client/entities/Currency.java
    handleAuthentication(balanceRequest);

    balanceRequest.setCurrencies("btc,ltc,ppc,doge,vtc,nmc,dash,usd,eur");
    return coingiAuthenticated.getUserBalance(balanceRequest);
  }

  public TransactionList getTransactions(TransactionHistoryRequest request) throws IOException {
    handleAuthentication(request);
    return coingiAuthenticated.getTransactionHistory(request);
  }

  public WithdrawalResponse withdraw(WithdrawalRequest request) throws IOException {
    handleAuthentication(request);
    return coingiAuthenticated.createWithdrawal(request);
  }
}
