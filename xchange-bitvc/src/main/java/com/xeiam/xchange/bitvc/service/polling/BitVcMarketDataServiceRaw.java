package com.xeiam.xchange.bitvc.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitvc.BitVc;
import com.xeiam.xchange.bitvc.BitVcExchange;
import com.xeiam.xchange.bitvc.dto.marketdata.BitVcDepth;
import com.xeiam.xchange.bitvc.dto.marketdata.BitVcOrderBookTAS;
import com.xeiam.xchange.bitvc.dto.marketdata.BitVcTicker;

public class BitVcMarketDataServiceRaw extends BitVcBasePollingService {

  private final BitVc bitvc;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitVcMarketDataServiceRaw(Exchange exchange) {

    super(exchange);

    final String baseUrl = (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem(BitVcExchange.URI_HUOBI_MARKETDATA);
    bitvc = RestProxyFactory.createProxy(BitVc.class, baseUrl);
  }

  public BitVcTicker getBitVcTicker(String symbol) throws IOException {

    return bitvc.getTicker(symbol);
  }

  public BitVcDepth getBitVcDepth(String symbol) throws IOException {

    return bitvc.getDepth(symbol);
  }

  public String[][] getBitVcKline(String symbol, String period) throws IOException {

    return bitvc.getKline(symbol, period);
  }

  public BitVcOrderBookTAS getBitVcDetail(String symbol) throws IOException {

    return bitvc.getDetail(symbol);
  }

}
