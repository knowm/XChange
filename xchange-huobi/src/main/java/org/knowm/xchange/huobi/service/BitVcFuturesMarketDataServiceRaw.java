package org.knowm.xchange.huobi.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.huobi.BitVcFutures;
import org.knowm.xchange.huobi.FuturesContract;
import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcExchangeRate;
import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcFuturesDepth;
import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcFuturesTicker;
import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcFuturesTrade;

import si.mazi.rescu.RestProxyFactory;

public class BitVcFuturesMarketDataServiceRaw extends HuobiBaseService {

  private final BitVcFutures bitvc;
  private final FuturesContract contract;

  /**
   * Constructor
   *
   * @param exchange
   * @param contract
   */
  public BitVcFuturesMarketDataServiceRaw(Exchange exchange, FuturesContract contract) {

    super(exchange);

    this.bitvc = RestProxyFactory.createProxy(BitVcFutures.class, "http://market.bitvc.com/futures/");
    this.contract = contract;
  }

  public BitVcFuturesTicker getBitVcTicker(String symbol) throws IOException {

    return bitvc.getTicker(symbol, contract.getName());
  }

  public BitVcFuturesDepth getBitVcDepth(String symbol) throws IOException {

    return bitvc.getDepths(symbol, contract.getName());
  }

  public BitVcFuturesTrade[] getBitVcTrades(String symbol) throws IOException {

    return bitvc.getTrades(symbol, contract.getName());
  }

  public BitVcExchangeRate getBitVcExchangeRate() throws IOException {

    return bitvc.getExchangeRate();
  }
}
