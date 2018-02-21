package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmex.Bitmex;
import org.knowm.xchange.bitmex.BitmexException;
import org.knowm.xchange.bitmex.dto.account.BitmexAccount;
import org.knowm.xchange.bitmex.dto.account.BitmexMarginAccount;
import org.knowm.xchange.bitmex.dto.account.BitmexWallet;
import org.knowm.xchange.bitmex.dto.account.BitmexWalletTransaction;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;

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

    public BitmexWallet getBitmexWallet(Currency... ccy) throws IOException {

    try {
      return bitmex.getWallet(apiKey, exchange.getNonceFactory(), signatureCreator/*, ccy.length>0?ccy[0].getCurrencyCode():null*/);
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

    public String requestDepositAddress(String currency) throws IOException {
        try {
            return bitmex.getDepositAddress(apiKey, exchange.getNonceFactory(), signatureCreator, currency);
        } catch (BitmexException e) {
            throw handleError(e);
        }
    }

    public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {
        try {
            BitmexWalletTransaction transaction = bitmex.withdrawFunds(apiKey, exchange.getNonceFactory(), signatureCreator, currency, amount, address);
            return transaction.getTransactID();
        } catch (BitmexException e) {
            throw handleError(e);
        }
    }

}
