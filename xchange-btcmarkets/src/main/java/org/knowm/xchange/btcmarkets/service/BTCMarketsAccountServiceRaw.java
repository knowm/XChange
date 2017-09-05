package org.knowm.xchange.btcmarkets.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcmarkets.BTCMarketsAuthenticated;
import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsBalance;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsWithdrawCryptoRequest;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsWithdrawCryptoResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.exceptions.ExchangeException;

import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BTCMarketsAccountServiceRaw extends BTCMarketsBaseService {

  private static final BigDecimal AMOUNT_MULTIPLICAND = new BigDecimal("100000000");
  private static final int MAX_SCALE = 8;

  private final BTCMarketsDigest signer;
  private final BTCMarketsAuthenticated btcm;
  private final SynchronizedValueFactory<Long> nonceFactory;

  protected BTCMarketsAccountServiceRaw(Exchange exchange) {
    super(exchange);
    this.nonceFactory = exchange.getNonceFactory();
    this.btcm = RestProxyFactory.createProxy(BTCMarketsAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.signer = new BTCMarketsDigest(exchange.getExchangeSpecification().getSecretKey());
  }

  public List<BTCMarketsBalance> getBTCMarketsBalance() throws IOException {
    return btcm.getBalance(exchange.getExchangeSpecification().getApiKey(), nonceFactory, signer);
  }

  public String withdrawCrypto(String address, BigDecimal amount, Currency currency) throws IOException {
    if (amount.scale() > MAX_SCALE) {
      throw new IllegalArgumentException("Amount scale exceed (" + MAX_SCALE + "), cannot safely convert into correct units");
    }

    long amountInSatoshis = amount.multiply(AMOUNT_MULTIPLICAND).longValue();

    BTCMarketsWithdrawCryptoRequest request = new BTCMarketsWithdrawCryptoRequest(amountInSatoshis, address, currency.getCurrencyCode());
    BTCMarketsWithdrawCryptoResponse response = btcm.withdrawCrypto(exchange.getExchangeSpecification().getApiKey(), nonceFactory, signer, request);

    if (!response.getSuccess())
      throw new ExchangeException("failed to withdraw funds: " + response.getErrorMessage() + " " + response.getErrorCode());

    return response.status;
  }
}
