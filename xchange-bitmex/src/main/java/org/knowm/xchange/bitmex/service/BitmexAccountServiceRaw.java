package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmex.BitmexException;
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

  public BitmexAccountServiceRaw(Exchange exchange) {

    super(exchange);

  }

  public BitmexAccount getBitmexAccountInfo() throws IOException {

    try {
      return bitmex.getAccount(apiKey, exchange.getNonceFactory(), signatureCreator);
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public BitmexWallet getBitmexWallet(Currency ccy) throws IOException {

    try {
      return bitmex.getWallet(apiKey, exchange.getNonceFactory(), signatureCreator, ccy.getCurrencyCode());
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexWalletTransaction> getBitmexWalletHistory(Currency ccy) throws IOException {

    try {
      return bitmex.getWalletHistory(apiKey, exchange.getNonceFactory(), signatureCreator, ccy.getCurrencyCode());
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexWalletTransaction> getBitmexWalletSummary(Currency ccy) throws IOException {

    try {
      return bitmex.getWalletSummary(apiKey, exchange.getNonceFactory(), signatureCreator, ccy.getCurrencyCode());
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public BitmexMarginAccount getBitmexMarginAccountStatus(Currency ccy) throws IOException {

    try {
      return bitmex.getMarginAccountStatus(apiKey, exchange.getNonceFactory(), signatureCreator, ccy.getCurrencyCode());
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexMarginAccount> getBitmexMarginAccountsStatus() throws IOException {

    try {
      return bitmex.getMarginAccountsStatus(apiKey, exchange.getNonceFactory(), signatureCreator);
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

}
