package org.knowm.xchange.examples.quoine.trade;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.examples.quoine.QuoineExamplesUtils;
import org.knowm.xchange.quoine.dto.trade.QuoineOrderResponse;
import org.knowm.xchange.quoine.service.QuoineTradeServiceRaw;
import org.knowm.xchange.service.trade.TradeService;

public class PlaceMarketOrderDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = QuoineExamplesUtils.createExchange();
    TradeService tradeService = exchange.getTradeService();

    generic(tradeService);
    //    raw((QuoineTradeServiceRaw) tradeService);
  }

  private static void raw(QuoineTradeServiceRaw tradeServiceRaw) throws IOException {

    // place a market order
    QuoineOrderResponse quoinePlaceOrderResponse =
        tradeServiceRaw.placeMarketOrder(CurrencyPair.BTC_USD, "sell", new BigDecimal(".1"));
    System.out.println("QuoineOrderResponse return value: " + quoinePlaceOrderResponse.toString());

    // list all orders for BTC/USD
  }

  private static void generic(TradeService tradeService) throws IOException {

    // place a limit buy order
    MarketOrder marketOrder =
        new MarketOrder((OrderType.ASK), new BigDecimal(".1"), CurrencyPair.BTC_USD);
    String marketOrderReturnValue = tradeService.placeMarketOrder(marketOrder);
    System.out.println("Market Order return value: " + marketOrderReturnValue);
  }
}
