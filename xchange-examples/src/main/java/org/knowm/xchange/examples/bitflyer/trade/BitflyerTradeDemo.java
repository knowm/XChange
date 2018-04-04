package org.knowm.xchange.examples.bitflyer.trade;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitflyer.dto.trade.BitflyerExecution;
import org.knowm.xchange.bitflyer.dto.trade.BitflyerPosition;
import org.knowm.xchange.bitflyer.service.BitflyerTradeServiceRaw;
import org.knowm.xchange.examples.bitflyer.BitflyerDemoUtils;
import org.knowm.xchange.service.trade.TradeService;

public class BitflyerTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = BitflyerDemoUtils.createExchange();
    TradeService service = exchange.getTradeService();

    executionsInfo(service);
    positionsInfo(service);
  }

  private static void executionsInfo(TradeService service) throws IOException {
    // Get the margin information
    BitflyerTradeServiceRaw serviceRaw = (BitflyerTradeServiceRaw) service;

    List<BitflyerExecution> executions = serviceRaw.getExecutions();
    System.out.println(executions);
    executions = serviceRaw.getExecutions("BTC_JPY");
    System.out.println(executions);
  }

  private static void positionsInfo(TradeService service) throws IOException {
    // Get the margin information
    BitflyerTradeServiceRaw serviceRaw = (BitflyerTradeServiceRaw) service;

    List<BitflyerPosition> executions = serviceRaw.getPositions();
    System.out.println(executions);
    executions = serviceRaw.getPositions("BTC_JPY");
    System.out.println(executions);
  }
}
