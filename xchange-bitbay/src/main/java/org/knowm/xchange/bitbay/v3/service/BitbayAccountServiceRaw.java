package org.knowm.xchange.bitbay.v3.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.knowm.xchange.bitbay.v3.BitbayExchange;
import org.knowm.xchange.bitbay.v3.dto.BitbayBalances;
import org.knowm.xchange.bitbay.v3.dto.trade.BitbayBalancesHistoryQuery;
import org.knowm.xchange.utils.ObjectMapperHelper;

public class BitbayAccountServiceRaw extends BitbayBaseService {
  public BitbayAccountServiceRaw(BitbayExchange bitbayExchange) {
    super(bitbayExchange);
  }

  public List<BitbayBalances.BitbayBalance> balances() throws IOException {
    BitbayBalances response =
        bitbayAuthenticated.balance(apiKey, sign, exchange.getNonceFactory(), UUID.randomUUID());
    return response.getBalances();
  }

  public Map balanceHistory(BitbayBalancesHistoryQuery query) throws IOException {
    String jsonQuery = ObjectMapperHelper.toCompactJSON(query);

    return bitbayAuthenticated.balanceHistory(
        apiKey, sign, exchange.getNonceFactory(), UUID.randomUUID(), jsonQuery);
  }
}
