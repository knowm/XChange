package com.xeiam.xchange.huobi.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.huobi.BitVcFutures;
import com.xeiam.xchange.huobi.FuturesContract;
import com.xeiam.xchange.huobi.dto.marketdata.futures.BitVcExchangeRate;
import com.xeiam.xchange.huobi.dto.marketdata.futures.BitVcFuturesDepth;
import com.xeiam.xchange.huobi.dto.marketdata.futures.BitVcFuturesTicker;
import com.xeiam.xchange.huobi.dto.marketdata.futures.BitVcFuturesTrade;

import si.mazi.rescu.RestProxyFactory;

public class BitVcFuturesMarketDataServiceRaw extends HuobiBasePollingService {

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
