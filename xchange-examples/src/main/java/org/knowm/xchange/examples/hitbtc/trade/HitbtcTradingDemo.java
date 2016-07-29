package org.knowm.xchange.examples.hitbtc.trade;

import java.io.IOException;
import java.util.Arrays;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.examples.hitbtc.HitbtcExampleUtils;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcOwnTrade;
import org.knowm.xchange.hitbtc.service.polling.HitbtcTradeServiceRaw;
import org.knowm.xchange.service.polling.trade.PollingTradeService;
import org.knowm.xchange.service.polling.trade.params.DefaultTradeHistoryParamPaging;

public class HitbtcTradingDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = HitbtcExampleUtils.createExchange();
    PollingTradeService tradeService = exchange.getPollingTradeService();

    generic(tradeService);
    raw((HitbtcTradeServiceRaw) tradeService);
  }

  private static void generic(PollingTradeService tradeService) throws IOException {

    DefaultTradeHistoryParamPaging params = new DefaultTradeHistoryParamPaging();
    UserTrades accountInfo = tradeService.getTradeHistory(params);
    System.out.println(accountInfo);
  }

  private static void raw(HitbtcTradeServiceRaw tradeService) throws IOException {

    HitbtcOwnTrade[] trades = tradeService.getTradeHistoryRaw(0, 100, null);

    System.out.println(Arrays.toString(trades));
  }
}
