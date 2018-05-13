package org.knowm.xchange.bitbay.v3.service;

import java.util.List;
import java.util.UUID;
import org.knowm.xchange.bitbay.v3.BitbayExchange;
import org.knowm.xchange.bitbay.v3.dto.BitbayBalances;

public class BitbayAccountServiceRaw extends BitbayBaseService {
  public BitbayAccountServiceRaw(BitbayExchange bitbayExchange) {
    super(bitbayExchange);
  }

  public List<BitbayBalances.BitbayBalance> balances() {
    BitbayBalances response =
        bitbayAuthenticated.balance(apiKey, sign, exchange.getNonceFactory(), UUID.randomUUID());
    return response.getBalances();
  }
}
