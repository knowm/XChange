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

  public List<HitbtcBalance> getWalletRaw() throws IOException {
    return hitbtc.getNewBalance();
  }

  public String withdrawFundsRaw(Currency currency, BigDecimal amount, String address) throws HttpStatusIOException {
    Map response = hitbtc.payout(amount, currency.getCurrencyCode(), address);
    //todo: handle "not enough funds" case more gracefully - the service returns a 409 with this body > {"code":"InvalidArgument","message":"Balance not enough"}
    return response.get("transaction").toString();
  }

  public HitbtcInternalTransferResponse transferFunds(Currency currency, BigDecimal amount, HitbtcTransferType hitbtcTransferType) throws IOException {

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

  public List<HitbtcBalance> getPaymentBalance() throws IOException {
    return hitbtc.getPaymentBalance();
  }

  public String getDepositAddress(String currency) throws IOException {

    HitbtcAddress hitbtcDepositAddress = hitbtc.getHitbtcDepositAddress(currency);
    return hitbtcDepositAddress.getAddress();
  }

  public List<HitbtcTransaction> getTransactions() throws HttpStatusIOException {
    return hitbtc.transactions();
  }

}
