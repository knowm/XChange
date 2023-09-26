package info.bitrich.xchangestream.bybit;

import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.bybit.BybitAdapters;
import org.knowm.xchange.bybit.dto.trade.BybitUserTradeDto;
import org.knowm.xchange.dto.trade.UserTrade;

public class BybitStreamingAdapters {

  public static List<UserTrade> adaptStreamingUserTradeList(List<BybitUserTradeDto> data) {
    List<UserTrade> userTrades = new ArrayList<>();

    data.forEach(bybitUserTradeData -> userTrades.add(BybitAdapters.adaptUserTrade(bybitUserTradeData, bybitUserTradeData.getCategory())));

    return userTrades;
  }
}
