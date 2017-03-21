package org.knowm.xchange.hitbtc.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.hitbtc.dto.HitbtcException;
import org.knowm.xchange.hitbtc.dto.account.HitbtcBalance;
import org.knowm.xchange.hitbtc.dto.account.HitbtcBalanceResponse;
import org.knowm.xchange.hitbtc.dto.account.HitbtcDepositAddressResponse;

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
