package org.knowm.xchange.coingi.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.coingi.CoingiAuthenticated;
import org.knowm.xchange.coingi.dto.account.CoingiBalanceRequest;
import org.knowm.xchange.coingi.dto.account.CoingiBalances;
import org.knowm.xchange.coingi.dto.account.CoingiDepositWalletRequest;
import org.knowm.xchange.coingi.dto.account.CoingiDepositWalletResponse;
import org.knowm.xchange.coingi.dto.account.CoingiUserTransactionList;
import org.knowm.xchange.coingi.dto.account.CoingiWithdrawalRequest;
import org.knowm.xchange.coingi.dto.account.CoingiWithdrawalResponse;
import org.knowm.xchange.coingi.dto.trade.CoingiTransactionHistoryRequest;

public class CoingiAccountServiceRaw extends CoingiBaseService {
  private final CoingiAuthenticated coingiAuthenticated;

  protected CoingiAccountServiceRaw(Exchange exchange) {
    super(exchange);
    this.coingiAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                CoingiAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        CoingiDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getUserName(),
            apiKey);
  }

  public CoingiBalances getCoingiBalance() throws IOException {
    CoingiBalanceRequest balanceRequest = new CoingiBalanceRequest();
    // Currency list:
    // https://github.com/Coingi/exchange-java-client/blob/master/src/main/java/com/coingi/exchange/client/entities/Currency.java
    handleAuthentication(balanceRequest);

    balanceRequest.setCurrencies("btc,ltc,ppc,doge,vtc,nmc,dash,usd,eur,czk");
    return coingiAuthenticated.getUserBalance(balanceRequest);
  }

  public CoingiUserTransactionList getTransactions(CoingiTransactionHistoryRequest request)
      throws IOException {
    handleAuthentication(request);
    return coingiAuthenticated.getTransactionHistory(request);
  }

  public CoingiWithdrawalResponse withdraw(CoingiWithdrawalRequest request) throws IOException {
    handleAuthentication(request);
    return coingiAuthenticated.createWithdrawal(request);
  }

  public CoingiDepositWalletResponse depositWallet(CoingiDepositWalletRequest request)
      throws IOException {
    handleAuthentication(request);
    return coingiAuthenticated.depositWallet(request);
  }
}
