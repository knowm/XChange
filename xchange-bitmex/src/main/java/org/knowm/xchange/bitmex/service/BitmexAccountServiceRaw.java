package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.dto.account.BitmexAccount;
import org.knowm.xchange.bitmex.dto.account.BitmexMarginAccount;
import org.knowm.xchange.bitmex.dto.account.BitmexWallet;
import org.knowm.xchange.bitmex.dto.account.BitmexWalletTransaction;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.exceptions.ExchangeException;

@SuppressWarnings({"WeakerAccess", "JavaDoc", "unused"})
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

  public BitmexAccount getBitmexAccountInfo() throws ExchangeException {

    try {
      return updateRateLimit(
          bitmex.getAccount(apiKey, exchange.getNonceFactory(), signatureCreator));
    } catch (IOException e) {
      throw handleError(e);
    }
  }

  public BitmexWallet getBitmexWallet(Currency... ccy) throws ExchangeException {

    try {
      return updateRateLimit(
          bitmex.getWallet(
              apiKey,
              exchange.getNonceFactory(),
              signatureCreator /*, ccy.length>0?ccy[0].getCurrencyCode():null*/));
    } catch (IOException e) {
      throw handleError(e);
    }
  }

  public List<BitmexWalletTransaction> getBitmexWalletHistory(Currency ccy)
      throws ExchangeException {

    try {
      return updateRateLimit(
          bitmex.getWalletHistory(
              apiKey, exchange.getNonceFactory(), signatureCreator, ccy.getCurrencyCode()));
    } catch (IOException e) {
      throw handleError(e);
    }
  }

  public List<BitmexWalletTransaction> getBitmexWalletSummary(Currency ccy)
      throws ExchangeException {

    try {
      return updateRateLimit(
          bitmex.getWalletSummary(
              apiKey, exchange.getNonceFactory(), signatureCreator, ccy.getCurrencyCode()));
    } catch (IOException e) {
      throw handleError(e);
    }
  }

  public BitmexMarginAccount getBitmexMarginAccountStatus(Currency ccy) throws ExchangeException {

    try {
      return updateRateLimit(
          bitmex.getMarginAccountStatus(
              apiKey, exchange.getNonceFactory(), signatureCreator, ccy.getCurrencyCode()));
    } catch (IOException e) {
      throw handleError(e);
    }
  }

  public List<BitmexMarginAccount> getBitmexMarginAccountsStatus() throws ExchangeException {

    try {
      return updateRateLimit(
          bitmex.getMarginAccountsStatus(apiKey, exchange.getNonceFactory(), signatureCreator));
    } catch (IOException e) {
      throw handleError(e);
    }
  }

  public String requestDepositAddress(String currency) throws ExchangeException {
    try {
      return bitmex.getDepositAddress(
          apiKey, exchange.getNonceFactory(), signatureCreator, currency);
    } catch (IOException e) {
      throw handleError(e);
    }
  }

  public String withdrawFunds(String currency, BigDecimal amount, String address)
      throws ExchangeException {
    try {
      BitmexWalletTransaction transaction =
          updateRateLimit(
              bitmex.withdrawFunds(
                  apiKey, exchange.getNonceFactory(), signatureCreator, currency, amount, address));
      return transaction.getTransactID();
    } catch (IOException e) {
      throw handleError(e);
    }
  }
}
