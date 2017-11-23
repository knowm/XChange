package org.knowm.xchange.empoex.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.empoex.EmpoExUtils;
import org.knowm.xchange.empoex.dto.marketdata.EmpoExLevel;
import org.knowm.xchange.empoex.dto.marketdata.EmpoExTicker;
import org.knowm.xchange.empoex.dto.marketdata.EmpoExTrade;

public class EmpoExMarketDataServiceRaw extends EmpoExBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public EmpoExMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public List<EmpoExTicker> getEmpoExTickers() throws IOException {

    return empoEx.getEmpoExTickers();
  }

  public EmpoExTicker getEmpoExTicker(CurrencyPair currencyPair) throws IOException {

    String pairString = EmpoExUtils.toPairString(currencyPair);
    return empoEx.getEmpoExTicker(pairString).get(0);
  }

  public Map<String, List<EmpoExTrade>> getEmpoExTrades(CurrencyPair currencyPair) throws IOException {

    String pairString = EmpoExUtils.toPairString(currencyPair);
    return empoEx.getEmpoExTrades(pairString);
  }

  public Map<String, Map<String, List<EmpoExLevel>>> getEmpoExDepth(CurrencyPair currencyPair) throws IOException {

    String pairString = EmpoExUtils.toPairString(currencyPair);
    return empoEx.getEmpoExDepth(pairString);
  }

}
