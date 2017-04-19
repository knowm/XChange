package org.knowm.xchange.examples.btcchina.trade;

import static org.apache.commons.lang3.time.DateUtils.parseDate;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btcchina.BTCChinaExchange;
import org.knowm.xchange.btcchina.service.rest.BTCChinaTradeService.BTCChinaTradeHistoryParams;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BTCChinaTradeStat {

  private static final Logger log = LoggerFactory.getLogger(BTCChinaTradeStat.class);

  private final TradeService tradeService;

  public BTCChinaTradeStat(String accessKey, String secretKey) {
    final ExchangeSpecification spec = new ExchangeSpecification(BTCChinaExchange.class);
    spec.setApiKey(accessKey);
    spec.setSecretKey(secretKey);

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(spec);

    tradeService = exchange.getTradeService();
  }

  /**
   * Returns the trades executed in the specified time.
   * <p>
   * Seems the <code>since</code> parameter does not work from the server side, we have to do this trick by ourself.
   * </p>
   *
   * @param startTime inclusive
   * @param endTime exclusive
   * @return trade between the <code>startTime</code> and <code>endTime</code>.
   * @throws IOException indicates I/O exception in calling RESTful API.
   */
  private List<UserTrade> getUserTrades(Date startTime, Date endTime) throws IOException {
    final long start = startTime.getTime(), end = endTime.getTime();
    final List<UserTrade> trades = new ArrayList<>();
    final Integer pageLength = 1000;
    final String type = "all";
    final Integer startId = null;

    for (int pageNumber = 0; ; pageNumber++) {
      log.trace("pageNumber: {}", pageNumber);

      final BTCChinaTradeHistoryParams params = new BTCChinaTradeHistoryParams(pageLength, pageNumber, type, startTime, startId);
      final UserTrades userTrades = tradeService.getTradeHistory(params);

      long earliest = System.currentTimeMillis();

      for (UserTrade trade : userTrades.getUserTrades()) {
        long time = trade.getTimestamp().getTime();
        earliest = Math.min(earliest, time);

        if (time >= start && time < end) {
          trades.add(trade);
        }
      }

      log.trace("earliest: {}", earliest);
      if (userTrades.getTrades().isEmpty() || earliest < start) {
        break;
      }
    }

    Collections.sort(trades, new Comparator<UserTrade>() {
      @Override
      public int compare(UserTrade o1, UserTrade o2) {
        return new Integer(o1.getId()).compareTo(new Integer(o2.getId()));
      }
    });
    return trades;
  }

  private void stat(Date startTime, Date endTime) throws IOException {
    List<UserTrade> trades = getUserTrades(startTime, endTime);
    if (!trades.isEmpty()) {
      UserTrade first = trades.get(0);
      UserTrade last = trades.get(trades.size() - 1);
      log.info("{}({})-{}({}): {}", first.getTimestamp(), first.getId(), last.getTimestamp(), last.getId(), trades.size());
    }

    int bidCount = 0, askCount = 0;
    BigDecimal totalBid = BigDecimal.ZERO, totalAsk = BigDecimal.ZERO, totalBidTradable = BigDecimal.ZERO, totalAskTradable = BigDecimal.ZERO;

    for (UserTrade trade : trades) {
      switch (trade.getType()) {
        case BID:
          bidCount++;
          totalBid = totalBid.add(trade.getPrice().multiply(trade.getTradableAmount()));
          totalBidTradable = totalBidTradable.add(trade.getTradableAmount());
          break;
        case ASK:
          askCount++;
          totalAsk = totalAsk.add(trade.getPrice().multiply(trade.getTradableAmount()));
          totalAskTradable = totalAskTradable.add(trade.getTradableAmount());
          break;
        default:
          break;
      }
    }

    BigDecimal avgBid = totalBidTradable.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO
        : totalBid.divide(totalBidTradable, 8, RoundingMode.HALF_EVEN);
    BigDecimal avgAsk = totalAskTradable.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO
        : totalAsk.divide(totalAskTradable, 8, RoundingMode.HALF_EVEN);

    log.info("bid trade count: {}, ask trade count: {}", bidCount, askCount);
    log.info("Total bid: {}, tradable: {}, avg: {}", totalBid, totalBidTradable, avgBid);
    log.info("Total ask: {}, tradable: {}, avg: {}", totalAsk, totalAskTradable, avgAsk);
    log.info("Total: {}", totalBid.add(totalAsk));
  }

  public static void main(String[] args) throws ParseException, IOException {
    log.info("args: {}", Arrays.toString(args));
    final String accessKey = args[0], secretKey = args[1];
    final Date startTime = parseDate(args[2], "yyyy-MM-dd");
    final Date endTime = parseDate(args[3], "yyyy-MM-dd");

    BTCChinaTradeStat stat = new BTCChinaTradeStat(accessKey, secretKey);
    stat.stat(startTime, endTime);
  }

}
