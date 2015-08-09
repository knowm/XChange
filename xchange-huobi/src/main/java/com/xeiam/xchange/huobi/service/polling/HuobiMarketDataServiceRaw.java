package com.xeiam.xchange.huobi.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.huobi.BitVc;
import com.xeiam.xchange.huobi.HuobiExchange;
import com.xeiam.xchange.huobi.dto.marketdata.HuobiDepth;
import com.xeiam.xchange.huobi.dto.marketdata.HuobiOrderBookTAS;
import com.xeiam.xchange.huobi.dto.marketdata.HuobiTicker;

import si.mazi.rescu.RestProxyFactory;

public class HuobiMarketDataServiceRaw extends HuobiBasePollingService {

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
