package org.knowm.xchange.bitbay.v3.service;

import java.io.IOException;
import java.util.UUID;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbay.v3.dto.trade.BitbayUserTrades;
import org.knowm.xchange.bitbay.v3.dto.trade.BitbayUserTradesQuery;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.ObjectMapperHelper;

/** @author walec51 */
public class BitbayTradeServiceRaw extends BitbayBaseService {

  BitbayTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BitbayUserTrades getBitbayTransactions(BitbayUserTradesQuery query)
      throws IOException, ExchangeException {
    final String jsonQuery = ObjectMapperHelper.toCompactJSON(query);
    final BitbayUserTrades response =
        bitbayAuthenticated.getTransactionHistory(
            apiKey, sign, exchange.getNonceFactory(), UUID.randomUUID(), jsonQuery);
    checkError(response);
    return response;
  }
}
