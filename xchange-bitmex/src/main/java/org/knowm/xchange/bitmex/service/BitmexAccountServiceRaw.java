package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.bitmex.BitmexException;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.dto.account.BitmexAccount;
import org.knowm.xchange.bitmex.dto.account.BitmexMarginAccount;
import org.knowm.xchange.bitmex.dto.account.BitmexWallet;
import org.knowm.xchange.bitmex.dto.account.BitmexWalletTransaction;
import org.knowm.xchange.currency.Currency;

public class BitmexAccountServiceRaw extends BitmexBaseService {

  String apiKey = exchange.getExchangeSpecification().getApiKey();

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitmexAccountServiceRaw(BitmexExchange exchange) {

    super(exchange);
  }

  public BitmexAccount getBitmexAccountInfo() throws IOException {

    try {
      return updateRateLimit(
          bitmex.getAccount(apiKey, exchange.getNonceFactory(), signatureCreator));
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public BitmexWallet getBitmexWallet(Currency... ccy) throws IOException {

    try {
      return updateRateLimit(
          bitmex.getWallet(
              apiKey,
              exchange.getNonceFactory(),
              signatureCreator /*, ccy.length>0?ccy[0].getCurrencyCode():null*/));
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexWalletTransaction> getBitmexWalletHistory(Currency ccy) throws IOException {

    try {
      return updateRateLimit(
          bitmex.getWalletHistory(
              apiKey, exchange.getNonceFactory(), signatureCreator, ccy.getCurrencyCode()));
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexWalletTransaction> getBitmexWalletSummary(Currency ccy) throws IOException {

    try {
      return updateRateLimit(
          bitmex.getWalletSummary(
              apiKey, exchange.getNonceFactory(), signatureCreator, ccy.getCurrencyCode()));
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public BitmexMarginAccount getBitmexMarginAccountStatus(Currency ccy) throws IOException {

    try {
      return updateRateLimit(
          bitmex.getMarginAccountStatus(
              apiKey, exchange.getNonceFactory(), signatureCreator, ccy.getCurrencyCode()));
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexMarginAccount> getBitmexMarginAccountsStatus() throws IOException {

    try {
      return updateRateLimit(
          bitmex.getMarginAccountsStatus(apiKey, exchange.getNonceFactory(), signatureCreator));
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public String requestDepositAddress(String currency) throws IOException {
    try {
      return bitmex.getDepositAddress(
          apiKey, exchange.getNonceFactory(), signatureCreator, currency);
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public String withdrawFunds(String currency, BigDecimal amount, String address)
      throws IOException {
    try {
      BitmexWalletTransaction transaction =
          updateRateLimit(
              bitmex.withdrawFunds(
                  apiKey, exchange.getNonceFactory(), signatureCreator, currency, amount, address));
      return transaction.getTransactID();
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }
}
