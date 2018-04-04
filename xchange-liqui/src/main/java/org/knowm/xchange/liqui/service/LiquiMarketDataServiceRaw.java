package org.knowm.xchange.liqui.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.liqui.Liqui;
import org.knowm.xchange.liqui.dto.marketdata.LiquiDepth;
import org.knowm.xchange.liqui.dto.marketdata.LiquiPublicTrade;
import org.knowm.xchange.liqui.dto.marketdata.LiquiPublicTrades;
import org.knowm.xchange.liqui.dto.marketdata.LiquiTicker;

public class LiquiMarketDataServiceRaw extends LiquiBaseService {

  protected LiquiMarketDataServiceRaw(final Exchange exchange) {
    super(exchange);
  }

  public LiquiTicker getTicker(final CurrencyPair pair) {
    return checkResult(liqui.getTicker(new Liqui.Pairs(pair)))
        .get(new Liqui.Pairs(pair).toString());
  }

  public Map<String, LiquiTicker> getTicker(final List<CurrencyPair> pairs) {
    return checkResult(liqui.getTicker(new Liqui.Pairs(pairs)));
  }

  public Map<String, LiquiTicker> getAllTickers() {
    return getTicker(new ArrayList<>(exchange.getExchangeMetaData().getCurrencyPairs().keySet()));
  }

  public LiquiDepth getDepth(final CurrencyPair pair) {
    return checkResult(liqui.getDepth(new Liqui.Pairs(pair))).get(new Liqui.Pairs(pair).toString());
  }

  public Map<String, LiquiDepth> getDepth(final List<CurrencyPair> pairs) {
    return checkResult(liqui.getDepth(new Liqui.Pairs(pairs)));
  }

  public LiquiDepth getDepth(final CurrencyPair pair, final int limit) {
    return checkResult(liqui.getDepth(new Liqui.Pairs(pair), limit))
        .get(new Liqui.Pairs(pair).toString());
  }

  public Map<String, LiquiDepth> getDepth(final List<CurrencyPair> pairs, final int limit) {
    return checkResult(liqui.getDepth(new Liqui.Pairs(pairs), limit));
  }

  public Map<String, LiquiDepth> getAllDepths() {
    return checkResult(
        liqui.getDepth(
            new Liqui.Pairs(
                new ArrayList<>(exchange.getExchangeMetaData().getCurrencyPairs().keySet()))));
  }

  public Map<String, LiquiDepth> getAllDepths(final int limit) {
    return checkResult(
        liqui.getDepth(
            new Liqui.Pairs(
                new ArrayList<>(exchange.getExchangeMetaData().getCurrencyPairs().keySet())),
            limit));
  }

  public List<LiquiPublicTrade> getTrades(final CurrencyPair pair) {
    return checkResult(liqui.getTrades(new Liqui.Pairs(pair)))
        .get(new Liqui.Pairs(pair).toString())
        .getTrades();
  }

  public Map<String, LiquiPublicTrades> getTrades(final List<CurrencyPair> pairs) {
    return checkResult(liqui.getTrades(new Liqui.Pairs(pairs)));
  }

  public List<LiquiPublicTrade> getTrades(final CurrencyPair pair, final int limit) {
    return checkResult(liqui.getTrades(new Liqui.Pairs(pair), limit))
        .get(new Liqui.Pairs(pair).toString())
        .getTrades();
  }

  public Map<String, LiquiPublicTrades> getTrades(final List<CurrencyPair> pairs, final int limit) {
    return checkResult(liqui.getTrades(new Liqui.Pairs(pairs), limit));
  }
}
