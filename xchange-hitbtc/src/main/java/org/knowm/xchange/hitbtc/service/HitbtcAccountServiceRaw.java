package org.knowm.xchange.hitbtc.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.hitbtc.dto.HitbtcException;
import org.knowm.xchange.hitbtc.dto.account.HitbtcBalance;
import org.knowm.xchange.hitbtc.dto.account.HitbtcBalanceResponse;
import org.knowm.xchange.hitbtc.dto.account.HitbtcDepositAddressResponse;
import si.mazi.rescu.HttpStatusIOException;

import java.io.IOException;
import java.math.BigDecimal;
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

    try {
      HitbtcBalanceResponse hitbtcBalance = hitbtc.getHitbtcBalance(signatureCreator, exchange.getNonceFactory(), apiKey);
      return hitbtcBalance.getBalances();
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public String withdrawFundsRaw(Currency currency, BigDecimal amount, String address) throws HttpStatusIOException {
    Map response = hitbtc.payout(signatureCreator, exchange.getNonceFactory(), apiKey, amount, currency.getCurrencyCode(), address, null, true);
    //todo: handle "not enough funds" case more gracefullly - the service returns a 409 with this body > {"code":"InvalidArgument","message":"Balance not enough"}
    return response.get("transaction").toString();
  }

  public HitbtcBalanceResponse getAccountBaseInfoRaw() throws IOException {

    try {
      HitbtcBalanceResponse hitbtcBalanceResponse = hitbtc.getHitbtcBalance(signatureCreator, exchange.getNonceFactory(), apiKey);
      return hitbtcBalanceResponse;
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
}
