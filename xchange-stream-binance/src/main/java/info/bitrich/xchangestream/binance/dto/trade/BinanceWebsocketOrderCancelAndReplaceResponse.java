package info.bitrich.xchangestream.binance.dto.trade;

import java.util.List;
import org.knowm.xchange.binance.dto.trade.BinanceNewOrder;

public class BinanceWebsocketOrderCancelAndReplaceResponse {
  private String cancelResult;
  private String newOrderResult;
  private List<BinanceNewOrder> cancelResponse;
  private List<BinanceNewOrder> newOrderResponse;
}
