package info.bitrich.xchangestream.bitmex;

import info.bitrich.xchangestream.bitmex.dto.BitmexOrder;
import info.bitrich.xchangestream.bitmex.dto.BitmexTicker;
import info.bitrich.xchangestream.bitmex.dto.BitmexTrade;
import java.util.HashMap;
import java.util.Map;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.StopOrder;

@Slf4j
@UtilityClass
public class BitmexStreamingAdapters {

  public final Map<String, CurrencyPair> SYMBOL_TO_CURRENCY_PAIR = new HashMap<>();
  private final Map<CurrencyPair, String> CURRENCY_PAIR_TO_SYMBOL = new HashMap<>();

  public void putSymbolMapping(String symbol, CurrencyPair instrument) {
    SYMBOL_TO_CURRENCY_PAIR.put(symbol, instrument);
    CURRENCY_PAIR_TO_SYMBOL.put(instrument, symbol);
  }

  public String toString(CurrencyPair instrument) {
    if (instrument == null) {
      return null;
    }
    return CURRENCY_PAIR_TO_SYMBOL.get(instrument);
  }

  public CurrencyPair toCurrencyPair(String bitmexSymbol) {
    if (bitmexSymbol == null) {
      return null;
    }
    return SYMBOL_TO_CURRENCY_PAIR.get(bitmexSymbol);
  }


  public Ticker toTicker(BitmexTicker bitmexTicker) {
    return new Ticker.Builder()
        .ask(bitmexTicker.getAskPrice())
        .askSize(bitmexTicker.getAskSize())
        .bid(bitmexTicker.getBidPrice())
        .bidSize(bitmexTicker.getBidSize())
        .timestamp(BitmexAdapters.toDate(bitmexTicker.getTimestamp()))
        .instrument(bitmexTicker.getCurrencyPair())
        .build();
  }

  public Trade toTrade(BitmexTrade bitmexTrade) {
    return new Trade.Builder()
        .type(bitmexTrade.getSide())
        .originalAmount(bitmexTrade.getAssetAmount())
        .instrument(bitmexTrade.getCurrencyPair())
        .price(bitmexTrade.getPrice())
        .timestamp(BitmexAdapters.toDate(bitmexTrade.getTimestamp()))
        .id(bitmexTrade.getId())
        .build();
  }

  public Order toOrder(BitmexOrder bitmexOrder) {
    Order.Builder builder;
    switch (bitmexOrder.getBitmexOrderType()) {
      case STOP:
        builder = new StopOrder.Builder(bitmexOrder.getOrderType(), bitmexOrder.getInstrument())
            .stopPrice(bitmexOrder.getOriginalPrice());
        break;
      case MARKET:
        builder = new MarketOrder.Builder(bitmexOrder.getOrderType(), bitmexOrder.getInstrument());
        break;
      case LIMIT:
        builder = new LimitOrder.Builder(bitmexOrder.getOrderType(), bitmexOrder.getInstrument()).limitPrice(bitmexOrder.getOriginalPrice());
        break;
      case STOP_LIMIT:
        builder = new StopOrder.Builder(bitmexOrder.getOrderType(), bitmexOrder.getInstrument())
            .limitPrice(bitmexOrder.getOriginalPrice())
            .stopPrice(bitmexOrder.getStopPrice());
        break;
      case PEGGED:
      case MARKET_IF_TOUCHED:
      case LIMIT_IF_TOUCHED:
      case MARKET_WITH_LEFT_OVER_AS_LIMIT:
        log.warn("Unknown order type: {}", bitmexOrder.getOrderType());
        return null;
      default:
        throw new IllegalArgumentException("Can't map " + bitmexOrder.getOrderType());
    }

    return builder
        .originalAmount(bitmexOrder.getOriginalAmount())
        .id(bitmexOrder.getOrderId())
        .timestamp(BitmexAdapters.toDate(bitmexOrder.getUpdatedAt()))
        .cumulativeAmount(bitmexOrder.getCumulativeAmount())
        .averagePrice(bitmexOrder.getAveragePrice())
        .orderStatus(BitmexAdapters.toOrderStatus(bitmexOrder.getOrderStatus()))
        .userReference(bitmexOrder.getText())
        .build();
  }

}
