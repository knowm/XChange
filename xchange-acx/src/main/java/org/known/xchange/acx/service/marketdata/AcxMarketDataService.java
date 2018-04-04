package org.known.xchange.acx.service.marketdata;

import static org.known.xchange.acx.utils.AcxUtils.getAcxMarket;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.known.xchange.acx.AcxApi;
import org.known.xchange.acx.AcxMapper;
import org.known.xchange.acx.dto.AcxTrade;
import org.known.xchange.acx.dto.marketdata.AcxMarket;
import org.known.xchange.acx.dto.marketdata.AcxOrderBook;
import org.known.xchange.acx.utils.ArgUtils;

public class AcxMarketDataService implements MarketDataService {
  public static final int MAX_LIMIT = 200;

  private final AcxApi api;
  private final AcxMapper mapper;

  public AcxMarketDataService(AcxApi api, AcxMapper mapper) {
    this.api = api;
    this.mapper = mapper;
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    AcxMarket tickerData = api.getTicker(getAcxMarket(currencyPair));
    return mapper.mapTicker(currencyPair, tickerData);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    Integer bidsLimit = ArgUtils.tryGet(args, 0, Integer.class, MAX_LIMIT);
    Integer askLimit = ArgUtils.tryGet(args, 1, Integer.class, MAX_LIMIT);
    AcxOrderBook orderBook = api.getOrderBook(getAcxMarket(currencyPair), bidsLimit, askLimit);
    return mapper.mapOrderBook(currencyPair, orderBook);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    List<AcxTrade> trades = api.getTrades(getAcxMarket(currencyPair));
    return mapper.mapTrades(currencyPair, trades);
  }
}
