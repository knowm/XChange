package info.bitrich.xchangestream.bankera;

import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Observable;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.knowm.xchange.bankera.BankeraAdapters;
import org.knowm.xchange.bankera.dto.BankeraException;
import org.knowm.xchange.bankera.dto.marketdata.BankeraMarket;
import org.knowm.xchange.bankera.dto.marketdata.BankeraMarketInfo;
import org.knowm.xchange.bankera.dto.marketdata.BankeraOrderBook;
import org.knowm.xchange.bankera.service.BankeraMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;

public class BankeraStreamingMarketDataService implements StreamingMarketDataService {

  private final BankeraStreamingService service;
  private final BankeraMarketDataService marketDataService;

  public BankeraStreamingMarketDataService(
      BankeraStreamingService service, BankeraMarketDataService marketDataService) {
    this.service = service;
    this.marketDataService = marketDataService;
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    BankeraMarket market = getMarketInfo(currencyPair);
    return service
        .subscribeChannel("market-orderbook", market.getId())
        .map(
            o -> {
              List<BankeraOrderBook.OrderBookOrder> listBids = new ArrayList<>();
              List<BankeraOrderBook.OrderBookOrder> listAsks = new ArrayList<>();
              o.get("data")
                  .get("bids")
                  .forEach(
                      b ->
                          listBids.add(
                              new BankeraOrderBook.OrderBookOrder(
                                  0, b.get("price").asText(), b.get("amount").asText())));
              o.get("data")
                  .get("asks")
                  .forEach(
                      b ->
                          listAsks.add(
                              new BankeraOrderBook.OrderBookOrder(
                                  0, b.get("price").asText(), b.get("amount").asText())));
              return BankeraAdapters.adaptOrderBook(
                  new BankeraOrderBook(listBids, listAsks), currencyPair);
            });
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    BankeraMarket market = getMarketInfo(currencyPair);
    return service
        .subscribeChannel("market-trade", market.getId())
        .map(
            t ->
                new Trade.Builder()
                    .currencyPair(currencyPair)
                    .id("-1")
                    .price(new BigDecimal(t.get("data").get("price").asText()))
                    .originalAmount(new BigDecimal(t.get("data").get("amount").asText()))
                    .timestamp(new Date(t.get("data").get("time").asLong()))
                    .type(
                        t.get("data").get("side").asText().equals("SELL")
                            ? Order.OrderType.ASK
                            : Order.OrderType.BID)
                    .build());
  }

  private BankeraMarket getMarketInfo(CurrencyPair currencyPair) {
    try {
      BankeraMarketInfo info = this.marketDataService.getMarketInfo();
      Optional<BankeraMarket> market =
          info.getMarkets().stream()
              .filter(m -> m.getName().equals(currencyPair.toString().replace("/", "-")))
              .findFirst();

      if (market.isPresent()) {
        return market.get();
      }
      throw new BankeraException(404, "Unable to find market.");
    } catch (IOException e) {
      throw new BankeraException(404, "Unable to find market.");
    }
  }
}
