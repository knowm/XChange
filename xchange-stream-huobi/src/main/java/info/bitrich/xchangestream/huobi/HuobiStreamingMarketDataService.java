package info.bitrich.xchangestream.huobi;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.rxjava3.core.Flowable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.huobi.HuobiUtils;

public class HuobiStreamingMarketDataService implements StreamingMarketDataService {

  private final HuobiStreamingService streamingService;

  public HuobiStreamingMarketDataService(HuobiStreamingService streamingService) {
    this.streamingService = streamingService;
  }

  @Override
  public Flowable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    String channelName =
        "market."
            + HuobiUtils.createHuobiCurrencyPair(currencyPair)
            + ".depth."
            + (args.length == 0 ? "step0" : args[0].toString());

    return streamingService
        .subscribeChannel(channelName)
        .map(
            message -> {
              JsonNode tick = message.get("tick");
              Date ts = new Date(message.get("ts").longValue());
              JsonNode asks = tick.get("asks");
              JsonNode bids = tick.get("bids");

              List<LimitOrder> askOrders = new ArrayList<>();
              List<LimitOrder> bidOrders = new ArrayList<>();
              OrderBook orderBook = new OrderBook(ts, askOrders, bidOrders);

              for (int i = 0; i < asks.size(); i++) {
                JsonNode order = asks.get(i);
                BigDecimal price = order.get(0).decimalValue();
                BigDecimal amount = order.get(1).decimalValue();
                LimitOrder lo =
                    new LimitOrder(Order.OrderType.ASK, amount, currencyPair, null, ts, price);
                askOrders.add(lo);
              }

              for (int i = 0; i < bids.size(); i++) {
                JsonNode order = bids.get(i);
                BigDecimal price = order.get(0).decimalValue();
                BigDecimal amount = order.get(1).decimalValue();
                LimitOrder lo =
                    new LimitOrder(Order.OrderType.BID, amount, currencyPair, null, ts, price);
                bidOrders.add(lo);
              }
              return orderBook;
            });
  }

  @Override
  public Flowable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    String channelName =
        "market."
            + HuobiUtils.createHuobiCurrencyPair(currencyPair)
            + ".kline."
            + (args.length == 0 ? "1min" : args[0].toString());

    return streamingService
        .subscribeChannel(channelName)
        .map(
            message -> {
              JsonNode data = message.get("tick");
              Date ts = new Date(message.get("ts").longValue());
              BigDecimal amount = data.get("amount").decimalValue(); // 成交量
              BigDecimal open = data.get("open").decimalValue(); // 开盘价
              BigDecimal close = data.get("close").decimalValue(); // 收盘价,当K线为最晚的一根时，是最新成交价
              BigDecimal low = data.get("low").decimalValue(); // 最低价
              BigDecimal high = data.get("high").decimalValue(); // 最高价

              Integer count = data.get("count").intValue(); // 成交笔数
              BigDecimal vol = data.get("vol").decimalValue(); // 成交额,即sum(每一笔成交价 * 该笔的成交量)

              // https://huobiapi.github.io/docs/spot/v1/en/#market-candlestick
              Ticker.Builder tickerBuilder = new Ticker.Builder();
              tickerBuilder.currencyPair(currencyPair);
              tickerBuilder.open(open);
              tickerBuilder.last(close);
              tickerBuilder.high(high);
              tickerBuilder.low(low);
              tickerBuilder.timestamp(ts);
              tickerBuilder.volume(amount);
              tickerBuilder.quoteVolume(vol);
              return tickerBuilder.build();
            });
  }

  @Override
  public Flowable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    String channelName =
        "market." + HuobiUtils.createHuobiCurrencyPair(currencyPair) + ".trade.detail";
    return streamingService
        .subscribeChannel(channelName)
        .map(
            message -> {
              JsonNode data = message.get("tick").get("data");
              List<Trade> list = new ArrayList<>();
              for (int i = 0; i < data.size(); i++) {
                JsonNode json = data.get(i);
                String direction = json.get("direction").textValue();
                Order.OrderType orderType = null;
                switch (direction) {
                  case "buy":
                    orderType = Order.OrderType.ASK;
                    break;
                  case "sell":
                    orderType = Order.OrderType.BID;
                    break;
                  default:
                    break;
                }
                if (orderType == null) {
                  return null;
                }
                BigDecimal price = json.get("price").decimalValue();
                Date ts = new Date(json.get("ts").longValue());
                String id = json.get("id").textValue();
                BigDecimal amount = json.get("amount").decimalValue();
                Trade trade = new Trade(orderType, amount, currencyPair, price, ts, id, null, null);
                list.add(trade);
              }
              return list.get(list.size() - 1);
            });
  }
}
