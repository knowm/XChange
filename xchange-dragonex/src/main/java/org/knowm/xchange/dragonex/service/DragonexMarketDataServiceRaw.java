package org.knowm.xchange.dragonex.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dragonex.dto.DragonResult;
import org.knowm.xchange.dragonex.dto.DragonexException;
import org.knowm.xchange.dragonex.dto.marketdata.Coin;
import org.knowm.xchange.dragonex.dto.marketdata.Depth;
import org.knowm.xchange.dragonex.dto.marketdata.Order;
import org.knowm.xchange.dragonex.dto.marketdata.Symbol;
import org.knowm.xchange.dragonex.dto.marketdata.Ticker;

public class DragonexMarketDataServiceRaw extends DragonexBaseService {

  public DragonexMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<Coin> coinAll() throws DragonexException, IOException {
    DragonResult<List<Coin>> coinAll = exchange.dragonexPublic().coinAll();
    return coinAll.getResult();
  }

  public List<Symbol> symbolAll() throws DragonexException, IOException {
    DragonResult<List<Symbol>> symbolAll = exchange.dragonexPublic().symbolAll();
    return symbolAll.getResult();
  }

  public List<Order> marketBuyOrders(long symbolId) throws DragonexException, IOException {
    DragonResult<List<Order>> marketBuyOrders = exchange.dragonexPublic().marketBuyOrders(symbolId);
    return marketBuyOrders.getResult();
  }

  public List<Order> marketSellOrders(long symbolId) throws DragonexException, IOException {
    DragonResult<List<Order>> marketSellOrders =
        exchange.dragonexPublic().marketSellOrders(symbolId);
    return marketSellOrders.getResult();
  }

  public Depth marketDepth(long symbolId, int count) throws DragonexException, IOException {
    DragonResult<Depth> marketDepth = exchange.dragonexPublic().marketDepth(symbolId, count);
    return marketDepth.getResult();
  }

  List<Ticker> ticker(long symbolId) throws DragonexException, IOException {
    DragonResult<List<Ticker>> ticker = exchange.dragonexPublic().ticker(symbolId);
    return ticker.getResult();
  }
}
