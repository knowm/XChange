package org.knowm.xchange.btcmarkets.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsBalance;
import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsFundtransferHistoryResponse;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsWithdrawCryptoRequest;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsWithdrawCryptoResponse;
import org.knowm.xchange.btcmarkets.dto.v3.account.BTCMarketsAddressesResponse;
import org.knowm.xchange.btcmarkets.dto.v3.account.BTCMarketsTradingFeesResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.exceptions.ExchangeException;

public class BTCMarketsAccountServiceRaw extends BTCMarketsBaseService {

  private static final BigDecimal AMOUNT_MULTIPLICAND = new BigDecimal("100000000");
  private static final int MAX_SCALE = 8;

  protected BTCMarketsAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<BTCMarketsBalance> getBTCMarketsBalance() throws IOException {
    return btcm.getBalance(exchange.getExchangeSpecification().getApiKey(), nonceFactory, signerV1);
  }

  public String withdrawCrypto(String address, BigDecimal amount, Currency currency)
      throws IOException {
    if (amount.scale() > MAX_SCALE) {
      throw new IllegalArgumentException(
          "Amount scale exceed (" + MAX_SCALE + "), cannot safely convert into correct units");
    }

    long amountInSatoshis = amount.multiply(AMOUNT_MULTIPLICAND).longValue();

    BTCMarketsWithdrawCryptoRequest request =
        new BTCMarketsWithdrawCryptoRequest(amountInSatoshis, address, currency.getCurrencyCode());
    BTCMarketsWithdrawCryptoResponse response =
        btcm.withdrawCrypto(
            exchange.getExchangeSpecification().getApiKey(), nonceFactory, signerV1, request);

    if (!response.getSuccess())
      throw new ExchangeException(
          "failed to withdraw funds: "
              + response.getErrorMessage()
              + " "
              + response.getErrorCode());

    return response.status;
  }

  public BTCMarketsFundtransferHistoryResponse fundtransferHistory() throws IOException {
    BTCMarketsFundtransferHistoryResponse response =
        btcm.fundtransferHistory(
            exchange.getExchangeSpecification().getApiKey(), nonceFactory, signerV1);

    if (!response.getSuccess()) {
      throw new ExchangeException(
          "failed to retrieve fundtransfer history: "
              + response.getErrorMessage()
              + " "
              + response.getErrorCode());
    }
    return response;
  }

  public BTCMarketsAddressesResponse depositAddress(Currency currency) throws IOException {
    return btcmv3.depositAddress(
        exchange.getExchangeSpecification().getApiKey(),
        nonceFactory,
        signerV3,
        currency.getCurrencyCode());
  }

  public BTCMarketsTradingFeesResponse tradingFees() throws IOException {
    return btcmv3.tradingFees(
        exchange.getExchangeSpecification().getApiKey(),
        nonceFactory,
        signerV3);
  }
}
