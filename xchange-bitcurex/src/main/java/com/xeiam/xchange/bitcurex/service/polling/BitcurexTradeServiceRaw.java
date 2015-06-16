package com.xeiam.xchange.bitcurex.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexResponse;
import com.xeiam.xchange.bitcurex.dto.marketdata.account.BitcurexFunds;
import com.xeiam.xchange.bitcurex.dto.marketdata.trade.BitcurexOrder;
import com.xeiam.xchange.bitcurex.dto.marketdata.trade.BitcurexOrderResponse;
import com.xeiam.xchange.exceptions.ExchangeException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class BitcurexTradeServiceRaw extends BitcurexBasePollingService {
  /**
   * @param exchange
   */
  public BitcurexTradeServiceRaw(Exchange exchange) {

      super(exchange);
  }

  public BitcurexOrderResponse createBitcurexOffer(String market, BigDecimal limit, BigDecimal volume, String offerType) throws IOException, ExchangeException {

    BitcurexResponse<BitcurexOrderResponse> response = bitcurexAuthenticated.createOffer(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        market, limit, offerType, volume,
        exchange.getNonceFactory());

    if ("ok".equals(response.getStatus())) {
      return response.getData();
    } else {
      throw new ExchangeException("Error getting balance. " + response.getError());
    }
  }

  public List<BitcurexOrder> getBitcurexOrders(String market) throws IOException {
    BitcurexResponse<List<BitcurexOrder>> allOffers = bitcurexAuthenticated.getAllOffers(market,
        exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());

    System.out.println(allOffers);
    return allOffers.getData();
  }



}
