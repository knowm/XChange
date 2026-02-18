package info.bitrich.xchangestream.deribit;

import info.bitrich.xchangestream.deribit.dto.response.DeribitTickerNotification;
import info.bitrich.xchangestream.deribit.dto.response.DeribitTradeNotification;
import info.bitrich.xchangestream.deribit.dto.response.DeribitTradeNotification.TradeData;
import info.bitrich.xchangestream.deribit.dto.response.DeribitUserChangeNotification;
import info.bitrich.xchangestream.deribit.dto.response.DeribitUserTradeNotification;
import info.bitrich.xchangestream.deribit.dto.response.DeribitUserTradeNotification.UserTradeData;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import org.knowm.xchange.deribit.v2.DeribitAdapters;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.OpenPosition.MarginMode;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;

@UtilityClass
public class DeribitStreamingAdapters {

  public Ticker toTicker(DeribitTickerNotification notification) {
    DeribitTickerNotification.TickerData tickerData = notification.getParams().getData();

    Instrument instrument = DeribitAdapters.toInstrument(tickerData.getInstrumentName());
    if (instrument == null) {
      return null;
    }

    return new Ticker.Builder()
        .instrument(instrument)
        .last(tickerData.getLastPrice())
        .bid(tickerData.getBestBidPrice())
        .ask(tickerData.getBestAskPrice())
        .high(tickerData.getStats().getHigh())
        .low(tickerData.getStats().getLow())
        .volume(tickerData.getStats().getVolume())
        .quoteVolume(tickerData.getStats().getVolumeNotional())
        .timestamp(DeribitAdapters.toDate(tickerData.getTimestamp()))
        .bidSize(tickerData.getBestBidSize())
        .askSize(tickerData.getBestAskSize())
        .percentageChange(tickerData.getStats().getPriceChange())
        .build();
  }

  public Trade toTrade(DeribitTradeNotification notification) {
    TradeData tradeData = notification.getParams().getData().get(0);

    Instrument instrument = DeribitAdapters.toInstrument(tradeData.getInstrumentName());
    if (instrument == null) {
      return null;
    }

    return Trade.builder()
        .type(tradeData.getOrderSide())
        .originalAmount(tradeData.getAmount())
        .instrument(instrument)
        .price(tradeData.getPrice())
        .timestamp(DeribitAdapters.toDate(tradeData.getTimestamp()))
        .id(tradeData.getTradeId())
        .build();
  }

  public UserTrade toUserTrade(DeribitUserTradeNotification notification) {
    UserTradeData userTradeData = notification.getParams().getData().get(0);
    return UserTrade.builder()
        .orderId(userTradeData.getOrderId())
        .feeAmount(userTradeData.getFee())
        .feeCurrency(userTradeData.getFeeCurrency())
        .orderUserReference(userTradeData.getLabel())
        .type(userTradeData.getOrderSide())
        .originalAmount(userTradeData.getAmount())
        .instrument(DeribitAdapters.toInstrument(userTradeData.getInstrumentName()))
        .price(userTradeData.getPrice())
        .timestamp(DeribitAdapters.toDate(userTradeData.getTimestamp()))
        .id(userTradeData.getTradeId())
        .build();
  }

  public OpenPosition toOpenPosition(DeribitUserChangeNotification notification) {
    var deribitPosition =
        Optional.ofNullable(notification.getParams().getData().getPositions())
            .map(deribitPositions -> deribitPositions.get(0))
            .orElse(null);

    if (deribitPosition == null) {
      return null;
    }

    var size =
        deribitPosition.getSizeCurrency() != null
            ? deribitPosition.getSizeCurrency()
            : deribitPosition.getSize();
    return OpenPosition.builder()
        .instrument(DeribitAdapters.toInstrument(deribitPosition.getInstrumentName()))
        .type(deribitPosition.getPositionType())
        .marginMode(MarginMode.CROSS)
        .size(size)
        .price(deribitPosition.getAveragePrice())
        .unRealisedPnl(deribitPosition.getFloatingProfitLoss())
        .build();
  }
}
