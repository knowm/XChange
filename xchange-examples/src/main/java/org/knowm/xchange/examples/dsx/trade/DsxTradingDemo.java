package org.knowm.xchange.examples.dsx.trade;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dsx.dto.DsxOwnTrade;
import org.knowm.xchange.dsx.dto.DsxSort;
import org.knowm.xchange.dsx.service.DsxTradeServiceRaw;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.examples.dsx.DsxExampleUtils;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;

public class DsxTradingDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = DsxExampleUtils.createExchange();
    TradeService tradeService = exchange.getTradeService();

    generic(tradeService);
    raw((DsxTradeServiceRaw) tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException {

    DefaultTradeHistoryParamPaging params = new DefaultTradeHistoryParamPaging();
    UserTrades accountInfo = tradeService.getTradeHistory(params);
    System.out.println(accountInfo);
  }

  private static void raw(DsxTradeServiceRaw tradeService) throws IOException {

    List<DsxOwnTrade> trades = tradeService.getTradeHistoryRaw("TRXBTC", 100, 0L);
    System.out.println(Arrays.toString(trades.toArray()));

    trades =
        tradeService.getTradeHistoryRaw(
            "LTCBTC", DsxSort.SORT_ASCENDING, new Date(0), null, Integer.MAX_VALUE, 0L);
    System.out.println(Arrays.toString(trades.toArray()));

    trades =
        tradeService.getTradeHistoryRaw(
            "LTCBTC", DsxSort.SORT_DESCENDING, 0L, null, Integer.MAX_VALUE, 0L);
    System.out.println(Arrays.toString(trades.toArray()));
  }
}
