package org.knowm.xchange.examples.hitbtc.trade;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.examples.hitbtc.HitbtcExampleUtils;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOwnTrade;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSort;
import org.knowm.xchange.hitbtc.v2.service.HitbtcTradeServiceRaw;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;

public class HitbtcTradingDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = HitbtcExampleUtils.createExchange();
    TradeService tradeService = exchange.getTradeService();

    generic(tradeService);
    raw((HitbtcTradeServiceRaw) tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException {

    DefaultTradeHistoryParamPaging params = new DefaultTradeHistoryParamPaging();
    UserTrades accountInfo = tradeService.getTradeHistory(params);
    System.out.println(accountInfo);
  }

  private static void raw(HitbtcTradeServiceRaw tradeService) throws IOException {

    List<HitbtcOwnTrade> trades = tradeService.getTradeHistoryRaw("TRXBTC", 100, 0L);
    System.out.println(Arrays.toString(trades.toArray()));

    trades =
        tradeService.getTradeHistoryRaw(
            "LTCBTC", HitbtcSort.SORT_ASCENDING, new Date(0), null, Integer.MAX_VALUE, 0L);
    System.out.println(Arrays.toString(trades.toArray()));

    trades =
        tradeService.getTradeHistoryRaw(
            "LTCBTC", HitbtcSort.SORT_DESCENDING, 0L, null, Integer.MAX_VALUE, 0L);
    System.out.println(Arrays.toString(trades.toArray()));
  }
}
