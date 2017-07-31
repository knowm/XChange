package org.knowm.xchange.hitbtc.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.hitbtc.dto.HitbtcException;
import org.knowm.xchange.hitbtc.dto.InternalTransferResponse;
import org.knowm.xchange.hitbtc.dto.TransactionResponse;
import org.knowm.xchange.hitbtc.dto.TransactionsResponse;
import org.knowm.xchange.hitbtc.dto.account.HitbtcBalance;
import org.knowm.xchange.hitbtc.dto.account.HitbtcBalanceResponse;
import org.knowm.xchange.hitbtc.dto.account.HitbtcDepositAddressResponse;
import org.knowm.xchange.hitbtc.dto.account.HitbtcPaymentBalanceResponse;
import si.mazi.rescu.HttpStatusIOException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class HitbtcAccountServiceRaw extends HitbtcBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public HitbtcAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public HitbtcBalance[] getWalletRaw() throws IOException {
    return getTradingBalance().getBalances();
  }

  public String withdrawFundsRaw(Currency currency, BigDecimal amount, String address) throws HttpStatusIOException {
    Map response = hitbtc.payout(signatureCreator, exchange.getNonceFactory(), apiKey, amount, currency.getCurrencyCode(), address, null, true);
    //todo: handle "not enough funds" case more gracefully - the service returns a 409 with this body > {"code":"InvalidArgument","message":"Balance not enough"}
    return response.get("transaction").toString();
  }

  public String transferToTrading(Currency currency, BigDecimal amount) {
    InternalTransferResponse internalTransferResponse = hitbtc.transferToTrading(signatureCreator, exchange.getNonceFactory(), apiKey, amount, currency.getCurrencyCode());
    if (internalTransferResponse.transactionId == null) {
      throw new ExchangeException("transfer failed: " + internalTransferResponse);
    } else {
      return internalTransferResponse.transactionId;
    }
  }

  public String transferToMain(Currency currency, BigDecimal amount) {
    InternalTransferResponse internalTransferResponse = hitbtc.transferToMain(signatureCreator, exchange.getNonceFactory(), apiKey, amount, currency.getCurrencyCode());
    if (internalTransferResponse.transactionId == null) {
      throw new ExchangeException("transfer failed: " + internalTransferResponse);
    } else {
      return internalTransferResponse.transactionId;
    }
  }

  public HitbtcBalanceResponse getTradingBalance() throws IOException {
    try {
      return hitbtc.getTradingBalance(signatureCreator, exchange.getNonceFactory(), apiKey);
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcPaymentBalanceResponse getPaymentBalance() throws IOException {
    try {
      return hitbtc.getPaymentBalance(signatureCreator, exchange.getNonceFactory(), apiKey);
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public String getDepositAddress(String currency) throws IOException {

    try {
      HitbtcDepositAddressResponse hitbtcDepositAddressResponse = hitbtc.getHitbtcDepositAddress(currency, signatureCreator,
          exchange.getNonceFactory(), apiKey);
      return hitbtcDepositAddressResponse.getAddress();
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public List<TransactionResponse> transactions(Long offset, long limit, String direction) {
    limit = Math.min(1000, limit);
    TransactionsResponse transactions = hitbtc.transactions(signatureCreator, exchange.getNonceFactory(), apiKey, offset, limit, direction);
    return transactions.transactions;
  }

}
