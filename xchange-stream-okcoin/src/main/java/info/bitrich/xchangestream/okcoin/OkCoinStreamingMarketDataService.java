package info.bitrich.xchangestream.okcoin;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.okcoin.dto.OkCoinOrderbook;
import info.bitrich.xchangestream.okcoin.dto.OkCoinWebSocketTrade;
import info.bitrich.xchangestream.okcoin.dto.marketdata.FutureTicker;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Flowable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.okcoin.FuturesContract;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinDepth;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinTicker;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinTickerResponse;

/**
 * #### spot ####
 * https://github.com/okcoin-okex/API-docs-OKEx.com/blob/master/API-For-Spot-CN/%E5%B8%81%E5%B8%81%E4%BA%A4%E6%98%93WebSocket%20API.md
 * ##### future ####
 * https://github.com/okcoin-okex/API-docs-OKEx.com/blob/master/API-For-Futures-CN/%E5%90%88%E7%BA%A6%E4%BA%A4%E6%98%93WebSocket%20API.md
 * https://github.com/okcoin-okex/API-docs-OKEx.com/blob/master/API-For-Futures-EN/WebSocket%20API%20for%20FUTURES.md
 */
public class OkCoinStreamingMarketDataService implements StreamingMarketDataService {
  private final OkCoinStreamingService service;

  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
  private final Map<String, OkCoinOrderbook> orderbooks = new HashMap<>();

  OkCoinStreamingMarketDataService(OkCoinStreamingService service) {
    this.service = service;
  }

  /**
   * #### spot #### 2. ok_sub_spot_X_depth 订阅币币市场深度(200增量数据返回) 3. ok_sub_spot_X_depth_Y 订阅市场深度 ####
   * future #### 3. ok_sub_futureusd_X_depth_Y 订阅合约市场深度(200增量数据返回) 3. ok_sub_futureusd_X_depth_Y
   * Subscribe Contract Market Depth(Incremental) 4. ok_sub_futureusd_X_depth_Y_Z 订阅合约市场深度(全量返回) 4.
   * ok_sub_futureusd_X_depth_Y_Z Subscribe Contract Market Depth(Full)
   *
   * @param currencyPair Currency pair of the order book
   * @param args if the first arg is {@link FuturesContract} means future, the next arg is amount
   * @return
   */
  @Override
  public Flowable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    String channel =
        String.format(
            "ok_sub_spot_%s_%s_depth",
            currencyPair.base.toString().toLowerCase(),
            currencyPair.counter.toString().toLowerCase());

    if (args.length > 0) {
      if (args[0] instanceof FuturesContract) {
        FuturesContract contract = (FuturesContract) args[0];
        channel =
            String.format(
                "ok_sub_future%s_%s_depth_%s",
                currencyPair.counter.toString().toLowerCase(),
                currencyPair.base.toString().toLowerCase(),
                contract.getName());
        if (args.length > 1) {
          channel = channel + "_" + args[1];
        }
      } else {
        channel = channel + "_" + args[1];
      }
    }
    final String key = channel;

    return service
        .subscribeChannel(channel)
        .map(
            s -> {
              OkCoinOrderbook okCoinOrderbook;
              if (!orderbooks.containsKey(key)) {
                OkCoinDepth okCoinDepth = mapper.treeToValue(s.get("data"), OkCoinDepth.class);
                okCoinOrderbook = new OkCoinOrderbook(okCoinDepth);
                orderbooks.put(key, okCoinOrderbook);
              } else {
                okCoinOrderbook = orderbooks.get(key);
                if (s.get("data").has("asks")) {
                  if (s.get("data").get("asks").size() > 0) {
                    BigDecimal[][] askLevels =
                        mapper.treeToValue(s.get("data").get("asks"), BigDecimal[][].class);
                    okCoinOrderbook.updateLevels(askLevels, Order.OrderType.ASK);
                  }
                }

                if (s.get("data").has("bids")) {
                  if (s.get("data").get("bids").size() > 0) {
                    BigDecimal[][] bidLevels =
                        mapper.treeToValue(s.get("data").get("bids"), BigDecimal[][].class);
                    okCoinOrderbook.updateLevels(bidLevels, Order.OrderType.BID);
                  }
                }
              }

              return OkCoinAdapters.adaptOrderBook(
                  okCoinOrderbook.toOkCoinDepth(s.get("data").get("timestamp").asLong()),
                  currencyPair);
            });
  }

  /**
   * #### spot #### 1. ok_sub_spot_X_ticker 订阅行情数据
   *
   * @param currencyPair Currency pair of the ticker
   * @param args
   * @return
   */
  @Override
  public Flowable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    String channel =
        String.format(
            "ok_sub_spot_%s_%s_ticker",
            currencyPair.base.toString().toLowerCase(),
            currencyPair.counter.toString().toLowerCase());

    return service
        .subscribeChannel(channel)
        .map(
            s -> {
              // TODO: fix parsing of BigDecimal attribute val that has format: 1,625.23
              OkCoinTicker okCoinTicker = mapper.treeToValue(s.get("data"), OkCoinTicker.class);
              return OkCoinAdapters.adaptTicker(
                  new OkCoinTickerResponse(okCoinTicker), currencyPair);
            });
  }

  /**
   * #### future #### 1. ok_sub_futureusd_X_ticker_Y 订阅合约行情 1. ok_sub_futureusd_X_ticker_Y Subscribe
   * Contract Market Price
   *
   * @param currencyPair Currency pair of the ticker
   * @param contract {@link FuturesContract}
   * @return
   */
  public Flowable<FutureTicker> getFutureTicker(
      CurrencyPair currencyPair, FuturesContract contract) {
    String channel =
        String.format(
            "ok_sub_future%s_%s_ticker_%s",
            currencyPair.counter.toString().toLowerCase(),
            currencyPair.base.toString().toLowerCase(),
            contract.getName());
    return service
        .subscribeChannel(channel)
        .map(s -> mapper.treeToValue(s.get("data"), FutureTicker.class));
  }

  /**
   * #### spot #### 4. ok_sub_spot_X_deals 订阅成交记录
   *
   * <p>#### future #### 5. ok_sub_futureusd_X_trade_Y 订阅合约交易信息 5. ok_sub_futureusd_X_trade_Y
   * Subscribe Contract Trade Record
   *
   * @param currencyPair Currency pair of the trades
   * @param args the first arg {@link FuturesContract}
   * @return
   */
  @Override
  public Flowable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    String channel =
        String.format(
            "ok_sub_spot_%s_%s_deals",
            currencyPair.base.toString().toLowerCase(),
            currencyPair.counter.toString().toLowerCase());

    if (args.length > 0) {
      FuturesContract contract = (FuturesContract) args[0];
      channel =
          String.format(
              "ok_sub_future%s_%s_trade_%s",
              currencyPair.counter.toString().toLowerCase(),
              currencyPair.base.toString().toLowerCase(),
              contract.getName());
    }

    return service
        .subscribeChannel(channel)
        .map(
            s -> {
              String[][] trades = mapper.treeToValue(s.get("data"), String[][].class);

              // I don't know how to parse this array of arrays in Jacson.
              OkCoinWebSocketTrade[] okCoinTrades = new OkCoinWebSocketTrade[trades.length];
              for (int i = 0; i < trades.length; ++i) {
                OkCoinWebSocketTrade okCoinWebSocketTrade = new OkCoinWebSocketTrade(trades[i]);
                okCoinTrades[i] = okCoinWebSocketTrade;
              }

              return OkCoinAdapters.adaptTrades(okCoinTrades, currencyPair);
            })
        .flatMapIterable(Trades::getTrades);
  }
}
