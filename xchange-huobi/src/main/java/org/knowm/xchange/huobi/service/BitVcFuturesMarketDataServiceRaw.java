package org.knowm.xchange.huobi.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.huobi.FuturesContract;
import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcExchangeRate;
import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcFuturesDepth;
import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcFuturesTicker;
import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcFuturesTrade;

public class BitVcFuturesMarketDataServiceRaw extends BitVcBaseFuturesServiceRaw {

  private final FuturesContract contract;

  /**
   * Constructor
   *
   * @param exchange
   * @param contract
   */
  public BitVcFuturesMarketDataServiceRaw(Exchange exchange, FuturesContract contract) {

    super(exchange);

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
