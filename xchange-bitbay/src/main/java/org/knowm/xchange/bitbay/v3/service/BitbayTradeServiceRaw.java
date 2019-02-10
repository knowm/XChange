package org.knowm.xchange.bitbay.v3.service;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbay.v3.dto.trade.BitbayUserTrades;
import org.knowm.xchange.bitbay.v3.dto.trade.BitbayUserTradesQuery;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.ObjectMapperHelper;

/** @author walec51 */
public class BitbayTradeServiceRaw extends BitbayBaseService {

  private static final Pattern WHITESPACES = Pattern.compile("\\s\\s");

  BitbayTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BitbayUserTrades getBitbayTransactions(BitbayUserTradesQuery query)
      throws IOException, ExchangeException {
    String jsonQuery = ObjectMapperHelper.toJSON(query);
    jsonQuery = WHITESPACES.matcher(jsonQuery).replaceAll("");
    BitbayUserTrades response =
        bitbayAuthenticated.getTransactionHistory(
            apiKey, sign, exchange.getNonceFactory(), UUID.randomUUID(), jsonQuery);
    checkError(response);
    return response;
  }
}
