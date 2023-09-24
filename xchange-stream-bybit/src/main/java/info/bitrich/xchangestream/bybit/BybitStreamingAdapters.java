package info.bitrich.xchangestream.bybit;

import info.bitrich.xchangestream.bybit.dto.BybitUserTradeResponseDto.BybitUserTradeData;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.bybit.BybitAdapters;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;

public class BybitStreamingAdapters {

  public static List<UserTrade> adaptUserTrade(List<BybitUserTradeData> data) {
    List<UserTrade> userTrades = new ArrayList<>();

    data.forEach(bybitUserTradeData -> {
      Instrument instrument = BybitAdapters.adaptInstrument(bybitUserTradeData.getSymbol(), bybitUserTradeData.getCategory());
      userTrades.add(new UserTrade.Builder()
          .instrument(instrument)
          .feeAmount(bybitUserTradeData.getExecFee())
          .type(BybitAdapters.adaptSide(bybitUserTradeData.getSide()))
          .orderUserReference(bybitUserTradeData.getOrderLinkId())
          .id(bybitUserTradeData.getExecId())
          .orderId(bybitUserTradeData.getOrderId())
          .originalAmount(bybitUserTradeData.getExecQty())
          .price(bybitUserTradeData.getExecPrice())
          .timestamp(bybitUserTradeData.getExecTime())
          .feeCurrency(BybitAdapters.getFeeCurrency(bybitUserTradeData.getIsMaker(), bybitUserTradeData.getFeeRate(), instrument , bybitUserTradeData.getSide()))
          .build());
    });

    return userTrades;
  }
}
