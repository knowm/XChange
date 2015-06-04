package com.xeiam.xchange.examples.quoine.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.examples.quoine.QuoineExamplesUtils;
import com.xeiam.xchange.quoine.dto.trade.QuoineOrderResponse;
import com.xeiam.xchange.quoine.service.polling.QuoineTradeServiceRaw;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

public class PlaceMarketOrderDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = QuoineExamplesUtils.createExchange();
    PollingTradeService tradeService = exchange.getPollingTradeService();

    generic(tradeService);
    //    raw((QuoineTradeServiceRaw) tradeService);
  }

  private static void raw(QuoineTradeServiceRaw tradeServiceRaw) throws IOException {

    // place a market order
    QuoineOrderResponse quoinePlaceOrderResponse = tradeServiceRaw.placeMarketOrder(CurrencyPair.BTC_USD, "sell", new BigDecimal(".1"));
    System.out.println("QuoineOrderResponse return value: " + quoinePlaceOrderResponse.toString());

    // list all orders for BTC/USD
  }

  private static void generic(PollingTradeService tradeService) throws IOException {

    // place a limit buy order
    MarketOrder marketOrder = new MarketOrder((OrderType.ASK), new BigDecimal(".1"), CurrencyPair.BTC_USD);
    String marketOrderReturnValue = tradeService.placeMarketOrder(marketOrder);
    System.out.println("Market Order return value: " + marketOrderReturnValue);

  }
}
