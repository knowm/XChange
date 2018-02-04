package org.knowm.xchange.hitbtc.v2.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcAddress;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcBalance;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcInternalTransferResponse;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTransaction;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTransferType;

import si.mazi.rescu.HttpStatusIOException;

public class HitbtcAccountServiceRaw extends HitbtcBaseService {

  public HitbtcAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public String withdrawFundsRaw(Currency currency, BigDecimal amount, String address, String paymentId) throws HttpStatusIOException {
    Map response = hitbtc.payout(amount, currency.getCurrencyCode(), address, paymentId);

    /*
     * todo: if there isn't enough money we get a 400 with body:
     * {"error":{"code":20001,"message":"Insufficient funds","description":"Check that the funds are sufficient, given commissions"}} ...but currently
     * 400 errors don't reach this code
     */

    return response.get("id").toString();
  }

  public HitbtcInternalTransferResponse transferFunds(Currency currency, BigDecimal amount,
      HitbtcTransferType hitbtcTransferType) throws IOException {
    return hitbtc.transferToTrading(amount, currency.getCurrencyCode(), hitbtcTransferType.getType());
  }

  public String transferToTrading(Currency currency, BigDecimal amount) throws IOException {

    HitbtcInternalTransferResponse response = transferFunds(currency, amount, HitbtcTransferType.BANK_TO_EXCHANGE);

    if (response.id == null) {
      throw new ExchangeException("transfer failed: " + response);
    }
    return response.id;
  }

  public String transferToMain(Currency currency, BigDecimal amount) throws IOException {
    HitbtcInternalTransferResponse response = transferFunds(currency, amount, HitbtcTransferType.EXCHANGE_TO_BANK);

    if (response.id == null) {
      throw new ExchangeException("transfer failed: " + response);
    }
    return response.id;
  }

  public List<HitbtcBalance> getMainBalance() throws IOException {
    return hitbtc.getMainBalance();
  }

  public List<HitbtcBalance> getTradingBalance() throws IOException {
    return hitbtc.getTradingBalance();
  }

  public HitbtcAddress getDepositAddress(Currency currency) throws IOException {
    return hitbtc.getHitbtcDepositAddress(currency.toString());
  }

  public List<HitbtcTransaction> getTransactions(String currency, Integer limit, Integer offset) throws HttpStatusIOException {
    return hitbtc.transactions(currency, limit, offset);
  }

}
