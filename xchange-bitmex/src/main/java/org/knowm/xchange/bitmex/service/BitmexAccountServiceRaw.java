package org.knowm.xchange.bitmex.service;

import static org.knowm.xchange.bitmex.BitmexAdapters.adaptCurrency;

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
    return updateRateLimit(
        () -> bitmex.getAccount(apiKey, exchange.getNonceFactory(), signatureCreator));
  }

  public BitmexWallet getBitmexWallet(Currency... ccy) throws ExchangeException {

    return updateRateLimit(
        () ->
            bitmex.getWallet(
                apiKey,
                exchange.getNonceFactory(),
                signatureCreator /*, ccy.length>0?ccy[0].getCurrencyCode():null*/));
  }

  public List<BitmexWalletTransaction> getBitmexWalletHistory(Currency ccy)
      throws ExchangeException {
    return getBitmexWalletHistory(ccy, null, null);
  }

  public List<BitmexWalletTransaction> getBitmexWalletHistory(
      Currency ccy, Integer count, Long start) throws ExchangeException {

    return updateRateLimit(
        () ->
            bitmex.getWalletHistory(
                apiKey,
                exchange.getNonceFactory(),
                signatureCreator,
                adaptCurrency(ccy),
                count,
                start));
  }

  public List<BitmexWalletTransaction> getBitmexWalletSummary(Currency ccy)
      throws ExchangeException {

    return updateRateLimit(
        () ->
            bitmex.getWalletSummary(
                apiKey, exchange.getNonceFactory(), signatureCreator, ccy.getCurrencyCode()));
  }

  public BitmexMarginAccount getBitmexMarginAccountStatus() throws ExchangeException {
    return updateRateLimit(
        () ->
            bitmex.getMarginAccountStatus(
                apiKey, exchange.getNonceFactory(), signatureCreator, null));
  }

  public List<BitmexMarginAccount> getBitmexMarginAccountsStatus() throws ExchangeException {
    return updateRateLimit(
        () -> bitmex.getMarginAccountsStatus(apiKey, exchange.getNonceFactory(), signatureCreator));
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
    BitmexWalletTransaction transaction =
        updateRateLimit(
            () ->
                bitmex.withdrawFunds(
                    apiKey,
                    exchange.getNonceFactory(),
                    signatureCreator,
                    currency,
                    amount,
                    address));
    return transaction.getTransactID();
  }
}
