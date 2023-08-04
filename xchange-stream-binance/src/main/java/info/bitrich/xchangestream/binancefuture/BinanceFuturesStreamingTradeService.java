package info.bitrich.xchangestream.binancefuture;

import info.bitrich.xchangestream.binance.BinanceStreamingTradeService;
import info.bitrich.xchangestream.binance.BinanceUserDataStreamingService;

public class BinanceFuturesStreamingTradeService extends BinanceStreamingTradeService {

  public BinanceFuturesStreamingTradeService(
      BinanceUserDataStreamingService binanceUserDataStreamingService) {
    super(binanceUserDataStreamingService);
  }
}
