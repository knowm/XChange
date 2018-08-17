package org.knowm.xchange.coinsuper.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.knowm.xchange.Exchange;
//import org.knowm.xchange.binance.BinanceAuthenticated;
//import org.knowm.xchange.binance.BinanceExchange;
//import org.knowm.xchange.binance.dto.meta.exchangeinfo.BinanceExchangeInfo;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class CoinsuperBaseService extends BaseExchangeService implements BaseService {

  protected final Logger LOG = LoggerFactory.getLogger(getClass());

  protected final String apiKey;
  protected final String secretKey;
  
  protected CoinsuperBaseService(Exchange exchange) {

    super(exchange);
    
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.secretKey = exchange.getExchangeSpecification().getSecretKey();

  }
}

