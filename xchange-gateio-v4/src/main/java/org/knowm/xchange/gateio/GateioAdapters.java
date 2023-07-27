package org.knowm.xchange.gateio;

import lombok.experimental.UtilityClass;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.gateio.dto.account.GateioOrder;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawalRequest;
import org.knowm.xchange.gateio.dto.marketdata.GateioCurrencyPairDetails;
import org.knowm.xchange.gateio.dto.marketdata.GateioOrderBook;
import org.knowm.xchange.gateio.dto.marketdata.GateioTicker;
import org.knowm.xchange.gateio.service.params.DefaultGateioWithdrawFundsParams;
import org.knowm.xchange.instrument.Instrument;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@UtilityClass
public class GateioAdapters {


  public String toString(Currency currency) {
    return currency.getCurrencyCode();
  }


  public String toString(Instrument instrument) {
    if (instrument == null) {
      return null;
    }
    else {
      return String.format("%s_%s",
                      instrument.getBase().getCurrencyCode(),
                      instrument.getCounter().getCurrencyCode())
              .toUpperCase(Locale.ROOT);
    }
  }


  public Instrument toInstrument(String currencyCode) {
    var currencies = currencyCode.split("_");
    return new CurrencyPair(currencies[0], currencies[1]);
  }


  public OrderBook toOrderBook(GateioOrderBook gateioOrderBook, Instrument instrument) {
    List<LimitOrder> asks = gateioOrderBook.getAsks().stream()
        .map(priceSizeEntry -> new LimitOrder(OrderType.ASK, priceSizeEntry.getSize(), instrument, null, null, priceSizeEntry.getPrice()))
        .collect(Collectors.toList());

    List<LimitOrder> bids = gateioOrderBook.getBids().stream()
        .map(priceSizeEntry -> new LimitOrder(OrderType.BID, priceSizeEntry.getSize(), instrument, null, null, priceSizeEntry.getPrice()))
        .collect(Collectors.toList());

    return new OrderBook(Date.from(gateioOrderBook.getGeneratedAt()), asks, bids);
  }


  public InstrumentMetaData toInstrumentMetaData(GateioCurrencyPairDetails gateioCurrencyPairDetails) {
    return new InstrumentMetaData.Builder()
        .tradingFee(gateioCurrencyPairDetails.getFee())
        .minimumAmount(gateioCurrencyPairDetails.getMinAssetAmount())
        .counterMinimumAmount(gateioCurrencyPairDetails.getMinQuoteAmount())
        .volumeScale(gateioCurrencyPairDetails.getAssetScale())
        .priceScale(gateioCurrencyPairDetails.getQuoteScale())
        .build();
  }


  public String toString(OrderStatus orderStatus) {
    switch (orderStatus) {
      case OPEN:
        return "open";
      case CLOSED:
        return "finished";
      default:
        throw new IllegalArgumentException("Can't map " + orderStatus);
    }
  }


  public OrderStatus toOrderStatus(String gateioOrderStatus) {
    switch (gateioOrderStatus) {
      case "open":
        return OrderStatus.OPEN;
      case "filled":
      case "closed":
        return OrderStatus.FILLED;
      case "cancelled":
      case "stp":
        return OrderStatus.CANCELED;
      default:
        throw new IllegalArgumentException("Can't map " + gateioOrderStatus);
    }
  }


  public GateioOrder toGateioOrder(MarketOrder marketOrder) {
    return GateioOrder.builder()
        .currencyPair(toString(marketOrder.getInstrument()))
        .side(toString(marketOrder.getType()))
        .clientOrderId(marketOrder.getUserReference())
        .account("spot")
        .type("market")
        .timeInForce("ioc")
        .amount(marketOrder.getOriginalAmount())
        .build();
  }


  public GateioOrder toGateioOrder(LimitOrder limitOrder) {
    return GateioOrder.builder()
        .currencyPair(toString(limitOrder.getInstrument()))
        .side(toString(limitOrder.getType()))
        .clientOrderId(limitOrder.getUserReference())
        .account("spot")
        .type("limit")
        .timeInForce("gtc")
        .price(limitOrder.getLimitPrice())
        .amount(limitOrder.getOriginalAmount())
        .build();
  }


  public Order toOrder(GateioOrder gateioOrder) {
    Order.Builder order;
    Instrument instrument = toInstrument(gateioOrder.getCurrencyPair());
    OrderType orderType = toOrderType(gateioOrder.getSide());

    switch (gateioOrder.getType()) {
      case "market":
        order = new MarketOrder.Builder(orderType, instrument);
        break;
      case "limit":
        order = new LimitOrder.Builder(orderType, instrument);
        break;
      default:
        throw new IllegalArgumentException("Can't map " + gateioOrder.getType());
    }

    return order
        .id(gateioOrder.getId())
        .originalAmount(gateioOrder.getAmount())
        .userReference(gateioOrder.getClientOrderId())
        .timestamp(Date.from(gateioOrder.getCreatedAt()))
        .orderStatus(toOrderStatus(gateioOrder.getStatus()))
        .cumulativeAmount(gateioOrder.getFilledTotalQuote())
        .averagePrice(gateioOrder.getAvgDealPrice())
        .fee(gateioOrder.getFee())
        .build();
  }


  public String toString(OrderType orderType) {
    switch (orderType) {
      case BID:
        return "buy";
      case ASK:
        return "sell";
      default:
        throw new IllegalArgumentException("Can't map " + orderType);
    }
  }


  public OrderType toOrderType(String gateioOrderType) {
    switch (gateioOrderType) {
      case "buy":
        return OrderType.BID;
      case "sell":
        return OrderType.ASK;
      default:
        throw new IllegalArgumentException("Can't map " + gateioOrderType);
    }
  }


  public GateioWithdrawalRequest toGateioWithdrawalRequest(DefaultGateioWithdrawFundsParams p) {
    return GateioWithdrawalRequest.builder()
        .clientRecordId(p.getClientRecordId())
        .address(p.getAddress())
        .tag(p.getAddressTag())
        .chain(p.getChain())
        .amount(p.getAmount())
        .currency(toString(p.getCurrency()))
        .build();

  }


  public Ticker toTicker(GateioTicker gateioTicker) {
    return new Ticker.Builder()
            .instrument(toInstrument(gateioTicker.getCurrencyPair()))
            .last(gateioTicker.getLastPrice())
            .bid(gateioTicker.getHighestBid())
            .ask(gateioTicker.getLowestAsk())
            .high(gateioTicker.getMaxPrice24h())
            .low(gateioTicker.getMinPrice24h())
            .volume(gateioTicker.getAssetVolume())
            .quoteVolume(gateioTicker.getQuoteVolume())
            .percentageChange(gateioTicker.getChangePercentage24h())
            .build();
  }
}
