package com.xeiam.xchange.hitbtc.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.hitbtc.dto.HitbtcException;
import com.xeiam.xchange.hitbtc.dto.account.HitbtcBalance;
import com.xeiam.xchange.hitbtc.dto.account.HitbtcBalanceResponse;
import com.xeiam.xchange.hitbtc.dto.account.HitbtcDepositAddressResponse;

public class HitbtcAccountServiceRaw extends HitbtcBasePollingService {

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
      HitbtcDepositAddressResponse hitbtcDepositAddressResponse  = hitbtc.getHitbtcDepositAddress(currency, signatureCreator, exchange.getNonceFactory(), apiKey);
      return hitbtcDepositAddressResponse.getAddress();
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }
}
