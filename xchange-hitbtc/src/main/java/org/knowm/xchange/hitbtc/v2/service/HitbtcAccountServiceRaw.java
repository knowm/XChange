package org.knowm.xchange.hitbtc.v2.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.hitbtc.dto.account.HitbtcPaymentBalanceResponse;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcBalance;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcDepositAddressResponse;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcInternalTransferResponse;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTransaction;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTransferType;
import org.knowm.xchange.hitbtc.v2.internal.api.HitbtcRestClient;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.HttpStatusIOException;

public class HitbtcAccountServiceRaw implements BaseService {

  public List<HitbtcBalance> getWalletRaw() throws IOException {
    return HitbtcRestClient.INSTANCE.call().getNewBalance();
  }

  public String withdrawFundsRaw(Currency currency, BigDecimal amount, String address) throws HttpStatusIOException {
    Map response = HitbtcRestClient.INSTANCE.call().payout(amount, currency.getCurrencyCode(), address);
    //todo: handle "not enough funds" case more gracefully - the service returns a 409 with this body > {"code":"InvalidArgument","message":"Balance not enough"}
    return response.get("transaction").toString();
  }

  public String transferFunds(Currency currency, BigDecimal amount, HitbtcTransferType hitbtcTransferType) throws HttpStatusIOException {

    HitbtcInternalTransferResponse internalTransferResponse = HitbtcRestClient.INSTANCE.call().transferToTrading(amount, currency.getCurrencyCode(), hitbtcTransferType.toString());

    if (internalTransferResponse.id == null) {
      throw new ExchangeException("transfer failed: " + internalTransferResponse);
    }

    return internalTransferResponse.id;
  }

  public HitbtcPaymentBalanceResponse getPaymentBalance() throws IOException {
    return HitbtcRestClient.INSTANCE.call().getPaymentBalance();
  }

  public String getDepositAddress(String currency) throws IOException {

    HitbtcDepositAddressResponse hitbtcDepositAddressResponse = HitbtcRestClient.INSTANCE.call().getHitbtcDepositAddress(currency);
    return hitbtcDepositAddressResponse.getAddress();
  }

  public List<HitbtcTransaction> transactions(Long offset, long limit, String direction) throws HttpStatusIOException {
    return HitbtcRestClient.INSTANCE.call().transactions();
  }

}
