package org.knowm.xchange.huobi.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.huobi.BitVc;
import org.knowm.xchange.huobi.HuobiExchange;
import org.knowm.xchange.huobi.dto.marketdata.HuobiDepth;
import org.knowm.xchange.huobi.dto.marketdata.HuobiOrderBookTAS;
import org.knowm.xchange.huobi.dto.marketdata.HuobiTicker;

import si.mazi.rescu.RestProxyFactory;

public class HuobiMarketDataServiceRaw extends HuobiBaseService {

  private final BitVc bitvc;

  /**
   * Constructor
   *
   * @param exchange
   */
  public HuobiMarketDataServiceRaw(Exchange exchange) {

    super(exchange);

    final String baseUrl = (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem(HuobiExchange.HUOBI_MARKET_DATA);
    bitvc = RestProxyFactory.createProxy(BitVc.class, baseUrl);
  }

  public HuobiTicker getBitVcTicker(String symbol) throws IOException {

    return bitvc.getTicker(symbol);
  }

  public HuobiDepth getBitVcDepth(String symbol) throws IOException {

    return bitvc.getDepth(symbol);
  }

  public String[][] getBitVcKline(String symbol, String period) throws IOException {

    return bitvc.getKline(symbol, period);
  }

  public HuobiOrderBookTAS getBitVcDetail(String symbol) throws IOException {

    return bitvc.getDetail(symbol);
  }

}
