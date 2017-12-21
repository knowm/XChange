package org.knowm.xchange.examples.bitmex.dto.trade;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmex.dto.trade.BitmexPosition;
import org.knowm.xchange.bitmex.dto.trade.BitmexTrade;
import org.knowm.xchange.bitmex.service.BitmexTradeServiceRaw;
import org.knowm.xchange.examples.bitmex.BitmexDemoUtils;
import org.knowm.xchange.service.trade.TradeService;

public class BitmexTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = BitmexDemoUtils.createExchange();
    TradeService tradeService = exchange.getTradeService();

    tradesInfo(tradeService);
    positionsInfo(tradeService);
  }

  private static void tradesInfo(TradeService service) throws IOException {

    BitmexTradeServiceRaw serviceRaw = (BitmexTradeServiceRaw) service;
    List<BitmexTrade> bitmexTrades = serviceRaw.getBitmexTrades();
    System.out.println(bitmexTrades);

    bitmexTrades = serviceRaw.getBitmexTrades("XBU:monthly");
    System.out.println(bitmexTrades);

  }

  private static void positionsInfo(TradeService service) throws IOException {

    BitmexTradeServiceRaw serviceRaw = (BitmexTradeServiceRaw) service;
    List<BitmexPosition> bitmexPositions = serviceRaw.getBitmexPositions();
    System.out.println(bitmexPositions);

    bitmexPositions = serviceRaw.getBitmexPositions(".BVOL7D");
    System.out.println(bitmexPositions);
  }
}